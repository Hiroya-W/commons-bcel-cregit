begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|jar
operator|.
name|JarOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ClassParser
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Constant
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantPool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantUtf8
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|JavaClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Utility
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
operator|.
name|ClassPath
import|;
end_import

begin_comment
comment|/**  * Package the client. Creates a jar file in the current directory that contains a minimal set of classes needed to run  * the client.  *  * Use BCEL to extract class names and read/write classes  */
end_comment

begin_class
specifier|public
class|class
name|Package
block|{
comment|/**      * The name of the resulting jar is Client.jar      */
specifier|static
name|String
name|defaultJar
init|=
literal|"Client.jar"
decl_stmt|;
comment|/*      * See usage() for arguments. Create an instance and run that (just so not all members have to be static)      */
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
name|args
index|[]
parameter_list|)
block|{
specifier|final
name|Package
name|instance
init|=
operator|new
name|Package
argument_list|()
decl_stmt|;
try|try
block|{
name|instance
operator|.
name|go
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|instance
operator|.
name|usage
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * We use a "default ClassPath object which uses the environments CLASSPATH      */
name|ClassPath
name|classPath
init|=
name|ClassPath
operator|.
name|SYSTEM_CLASS_PATH
decl_stmt|;
comment|/**      * A map for all Classes, the ones we're going to package. Store class name against the JavaClass. From the JavaClass we      * get the bytes to create the jar.      */
name|Map
argument_list|<
name|String
argument_list|,
name|JavaClass
argument_list|>
name|allClasses
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * We start at the root classes, put them in here, then go through this list, putting dependent classes in here and from      * there into allClasses. Store class names against class names of their dependents      */
name|TreeMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|dependents
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Collect all classes that could not be found in the classpath. Store class names against class names of their      * dependents      */
name|TreeMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|notFound
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * See wheather we print the classes that were not found (default = false)      */
name|boolean
name|showNotFound
init|=
literal|false
decl_stmt|;
comment|/**      * Remember wheather to print allClasses at the end (default = false)      */
name|boolean
name|printClasses
init|=
literal|false
decl_stmt|;
comment|/**      * Wheather we log classes during processing (default = false)      */
name|boolean
name|log
init|=
literal|false
decl_stmt|;
comment|/**      * add given class to dependents (from is where its dependent from) some fiddeling to be done because of array class      * notation      */
name|void
name|addClassString
parameter_list|(
specifier|final
name|String
name|clas
parameter_list|,
specifier|final
name|String
name|from
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|log
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"processing: "
operator|+
name|clas
operator|+
literal|" referenced by "
operator|+
name|from
argument_list|)
expr_stmt|;
block|}
comment|// must check if it's an arrary (start with "[")
if|if
condition|(
name|clas
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
block|{
if|if
condition|(
name|clas
operator|.
name|length
argument_list|()
operator|==
literal|2
condition|)
block|{
comment|// it's an array of built in type, ignore
return|return;
block|}
if|if
condition|(
literal|'L'
operator|==
name|clas
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
condition|)
block|{
comment|// it's an array of objects, the class name is between [L and ;
comment|// like [Ljava/lang/Object;
name|addClassString
argument_list|(
name|clas
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
name|clas
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|,
name|from
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
literal|'['
operator|==
name|clas
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
condition|)
block|{
comment|// it's an array of arrays, call recursive
name|addClassString
argument_list|(
name|clas
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|,
name|from
argument_list|)
expr_stmt|;
return|return;
block|}
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Can't recognize class name ="
operator|+
name|clas
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|clas
operator|.
name|startsWith
argument_list|(
literal|"java/"
argument_list|)
operator|&&
name|allClasses
operator|.
name|get
argument_list|(
name|clas
argument_list|)
operator|==
literal|null
condition|)
block|{
name|dependents
operator|.
name|put
argument_list|(
name|clas
argument_list|,
name|from
argument_list|)
expr_stmt|;
comment|// System.out.println(" yes" );
block|}
else|else
block|{
comment|// System.out.println(" no" );
block|}
block|}
comment|/**      * Add this class to allClasses. Then go through all its dependents and add them to the dependents list if they are not      * in allClasses      */
name|void
name|addDependents
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|String
name|name
init|=
name|Utility
operator|.
name|packageToPath
argument_list|(
name|clazz
operator|.
name|getClassName
argument_list|()
argument_list|)
decl_stmt|;
name|allClasses
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
specifier|final
name|ConstantPool
name|pool
init|=
name|clazz
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|pool
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
specifier|final
name|Constant
name|cons
init|=
name|pool
operator|.
name|getConstant
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|// System.out.println("("+i+") " + cons );
if|if
condition|(
name|cons
operator|!=
literal|null
operator|&&
name|cons
operator|.
name|getTag
argument_list|()
operator|==
name|Const
operator|.
name|CONSTANT_Class
condition|)
block|{
specifier|final
name|int
name|idx
init|=
operator|(
operator|(
name|ConstantClass
operator|)
name|pool
operator|.
name|getConstant
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getNameIndex
argument_list|()
decl_stmt|;
specifier|final
name|String
name|clas
init|=
operator|(
operator|(
name|ConstantUtf8
operator|)
name|pool
operator|.
name|getConstant
argument_list|(
name|idx
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
decl_stmt|;
name|addClassString
argument_list|(
name|clas
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * the main of this class      */
name|void
name|go
parameter_list|(
specifier|final
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|IOException
block|{
name|JavaClass
name|clazz
decl_stmt|;
comment|// sort the options
for|for
control|(
specifier|final
name|String
name|arg
range|:
name|args
control|)
block|{
if|if
condition|(
name|arg
operator|.
name|startsWith
argument_list|(
literal|"-e"
argument_list|)
condition|)
block|{
name|showNotFound
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|arg
operator|.
name|startsWith
argument_list|(
literal|"-s"
argument_list|)
condition|)
block|{
name|printClasses
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|arg
operator|.
name|startsWith
argument_list|(
literal|"-l"
argument_list|)
condition|)
block|{
name|log
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
name|String
name|clName
init|=
name|arg
decl_stmt|;
if|if
condition|(
name|clName
operator|.
name|endsWith
argument_list|(
name|JavaClass
operator|.
name|EXTENSION
argument_list|)
condition|)
block|{
name|clName
operator|=
name|clName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|clName
operator|.
name|length
argument_list|()
operator|-
name|JavaClass
operator|.
name|EXTENSION
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|clName
operator|=
name|Utility
operator|.
name|packageToPath
argument_list|(
name|clName
argument_list|)
expr_stmt|;
try|try
init|(
specifier|final
name|InputStream
name|inputStream
init|=
name|classPath
operator|.
name|getInputStream
argument_list|(
name|clName
argument_list|)
init|)
block|{
name|clazz
operator|=
operator|new
name|ClassParser
argument_list|(
name|inputStream
argument_list|,
name|clName
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
block|}
comment|// here we create the root set of classes to process
name|addDependents
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Packaging for class: "
operator|+
name|clName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dependents
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|usage
argument_list|()
expr_stmt|;
return|return;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Creating jar file: "
operator|+
name|defaultJar
argument_list|)
expr_stmt|;
comment|// starting processing: Grab from the dependents list and add back to it
comment|// and the allClasses list. see addDependents
while|while
condition|(
operator|!
name|dependents
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
specifier|final
name|String
name|name
init|=
name|dependents
operator|.
name|firstKey
argument_list|()
decl_stmt|;
specifier|final
name|String
name|from
init|=
name|dependents
operator|.
name|remove
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|allClasses
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|==
literal|null
condition|)
block|{
try|try
init|(
specifier|final
name|InputStream
name|inputStream
init|=
name|classPath
operator|.
name|getInputStream
argument_list|(
name|name
argument_list|)
init|)
block|{
name|clazz
operator|=
operator|new
name|ClassParser
argument_list|(
name|inputStream
argument_list|,
name|name
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
name|addDependents
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|IOException
name|e
parameter_list|)
block|{
comment|// System.err.println("Error, class not found " + name );
name|notFound
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|from
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|printClasses
condition|)
block|{
comment|// if wanted show all classes
name|printAllClasses
argument_list|()
expr_stmt|;
block|}
comment|// create the jar
try|try
init|(
specifier|final
name|JarOutputStream
name|jarFile
init|=
operator|new
name|JarOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|defaultJar
argument_list|)
argument_list|)
init|)
block|{
name|jarFile
operator|.
name|setLevel
argument_list|(
literal|5
argument_list|)
expr_stmt|;
comment|// use compression
name|int
name|written
init|=
literal|0
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|name
range|:
name|allClasses
operator|.
name|keySet
argument_list|()
control|)
block|{
comment|// add entries for every class
specifier|final
name|JavaClass
name|claz
init|=
name|allClasses
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
specifier|final
name|ZipEntry
name|zipEntry
init|=
operator|new
name|ZipEntry
argument_list|(
name|name
operator|+
name|JavaClass
operator|.
name|EXTENSION
argument_list|)
decl_stmt|;
specifier|final
name|byte
index|[]
name|bytes
init|=
name|claz
operator|.
name|getBytes
argument_list|()
decl_stmt|;
specifier|final
name|int
name|length
init|=
name|bytes
operator|.
name|length
decl_stmt|;
name|jarFile
operator|.
name|putNextEntry
argument_list|(
name|zipEntry
argument_list|)
expr_stmt|;
name|jarFile
operator|.
name|write
argument_list|(
name|bytes
argument_list|,
literal|0
argument_list|,
name|length
argument_list|)
expr_stmt|;
name|written
operator|+=
name|length
expr_stmt|;
comment|// for logging
block|}
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The jar file contains "
operator|+
name|allClasses
operator|.
name|size
argument_list|()
operator|+
literal|" classes and contains "
operator|+
name|written
operator|+
literal|" bytes"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|notFound
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|notFound
operator|.
name|size
argument_list|()
operator|+
literal|" classes could not be found"
argument_list|)
expr_stmt|;
if|if
condition|(
name|showNotFound
condition|)
block|{
comment|// if wanted show the actual classes that we not found
while|while
condition|(
operator|!
name|notFound
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
specifier|final
name|String
name|name
init|=
name|notFound
operator|.
name|firstKey
argument_list|()
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|name
operator|+
literal|" ("
operator|+
name|notFound
operator|.
name|remove
argument_list|(
name|name
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Use '-e' option to view classes that were not found"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Print all classes that were packaged. Sort alphabetically for better overview. Enabled by -s option      */
name|void
name|printAllClasses
parameter_list|()
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|names
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|allClasses
operator|.
name|keySet
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|names
argument_list|)
expr_stmt|;
name|names
operator|.
name|forEach
argument_list|(
name|System
operator|.
name|err
operator|::
name|println
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|usage
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" This program packages classes and all their dependents"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" into one jar. Give all starting classes (your main)"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" on the command line. Use / as separator, the .class is"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" optional. We use the environments CLASSPATH to resolve"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" classes. Anything but java.* packages are packaged."
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" If you use Class.forName (or similar), be sure to"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" include the classes that you load dynamically on the"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" command line.\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" These options are recognized:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" -e -error  Show errors, meaning classes that could not "
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   resolved + the classes that referenced them."
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" -l -log  Show classes as they are processed. This will"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   include doubles, java classes and is difficult to"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   read. I use it as a sort of progress monitor"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" -s -show  Prints all the classes that were packaged"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   in alphabetical order, which is ordered by package"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   for the most part."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

