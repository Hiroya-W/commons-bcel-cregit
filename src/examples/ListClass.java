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
name|IOException
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
name|HashMap
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
name|Repository
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
name|Code
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
name|Method
import|;
end_import

begin_comment
comment|/**  * Read class file(s) and display its contents. The command line usage is:  *  *<pre>  * java ListClass [-constants] [-code] [-brief] [-dependencies] [-nocontents] [-recurse] class... [-exclude<list>]  *</pre>  *  * where  *<ul>  *<li>{@code -code} List byte code of methods</li>  *<li>{@code -brief} List byte codes briefly</li>  *<li>{@code -constants} Print constants table (constant pool)</li>  *<li>{@code -recurse} Usually intended to be used along with {@code -dependencies} When this flag is set, listclass  * will also print information about all classes which the target class depends on.</li>  *  *<li>{@code -dependencies} Setting this flag makes listclass print a list of all classes which the target class  * depends on. Generated from getting all CONSTANT_Class constants from the constant pool.</li>  *  *<li>{@code -exclude} All non-flag arguments after this flag are added to an 'exclusion list'. Target classes are  * compared with the members of the exclusion list. Any target class whose fully qualified name begins with a name in  * the exclusion list will not be analyzed/listed. This is meant primarily when using both {@code -recurse} to exclude  * java, javax, and sun classes, and is recommended as otherwise the output from {@code -recurse} gets quite long and  * most of it is not interesting. Note that {@code -exclude} prevents listing of classes, it does not prevent class  * names from being printed in the {@code -dependencies} list.</li>  *<li>{@code -nocontents} Do not print JavaClass.toString() for the class. I added this because sometimes I'm only  * interested in dependency information.</li>  *</ul>  *<p>  * Here's a couple examples of how I typically use ListClass:  *</p>  *  *<pre>  * java ListClass -code MyClass  *</pre>  *  * Print information about the class and the byte code of the methods  *  *<pre>  * java ListClass -nocontents -dependencies MyClass  *</pre>  *  * Print a list of all classes which MyClass depends on.  *  *<pre>  * java ListClass -nocontents -recurse MyClass -exclude java. javax. sun.  *</pre>  *  * Print a recursive listing of all classes which MyClass depends on. Do not analyze classes beginning with "java.",  * "javax.", or "sun.".  *  *<pre>  * java ListClass -nocontents -dependencies -recurse MyClass -exclude java.javax. sun.  *</pre>  *<p>  * Print a recursive listing of dependency information for MyClass and its dependents. Do not analyze classes beginning  * with "java.", "javax.", or "sun."  *</p>  *  *<a href="mailto:twheeler@objectspace.com">Thomas Wheeler</A>  */
end_comment

begin_class
specifier|public
class|class
name|ListClass
block|{
specifier|public
specifier|static
name|String
index|[]
name|getClassDependencies
parameter_list|(
specifier|final
name|ConstantPool
name|pool
parameter_list|)
block|{
specifier|final
name|String
index|[]
name|tempArray
init|=
operator|new
name|String
index|[
name|pool
operator|.
name|getLength
argument_list|()
index|]
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|pool
operator|.
name|getLength
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
specifier|final
name|Constant
name|c
init|=
name|pool
operator|.
name|getConstant
argument_list|(
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|c
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
name|ConstantUtf8
name|c1
init|=
operator|(
name|ConstantUtf8
operator|)
name|pool
operator|.
name|getConstant
argument_list|(
operator|(
operator|(
name|ConstantClass
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
argument_list|)
decl_stmt|;
name|buf
operator|.
name|setLength
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|c1
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|n
init|=
literal|0
init|;
name|n
operator|<
name|buf
operator|.
name|length
argument_list|()
condition|;
name|n
operator|++
control|)
block|{
if|if
condition|(
name|buf
operator|.
name|charAt
argument_list|(
name|n
argument_list|)
operator|==
literal|'/'
condition|)
block|{
name|buf
operator|.
name|setCharAt
argument_list|(
name|n
argument_list|,
literal|'.'
argument_list|)
expr_stmt|;
block|}
block|}
name|tempArray
index|[
name|size
operator|++
index|]
operator|=
name|buf
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
specifier|final
name|String
index|[]
name|dependencies
init|=
operator|new
name|String
index|[
name|size
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|tempArray
argument_list|,
literal|0
argument_list|,
name|dependencies
argument_list|,
literal|0
argument_list|,
name|size
argument_list|)
expr_stmt|;
return|return
name|dependencies
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|argv
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|fileName
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|excludeName
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|code
init|=
literal|false
decl_stmt|;
name|boolean
name|constants
init|=
literal|false
decl_stmt|;
name|boolean
name|verbose
init|=
literal|true
decl_stmt|;
name|boolean
name|classdep
init|=
literal|false
decl_stmt|;
name|boolean
name|nocontents
init|=
literal|false
decl_stmt|;
name|boolean
name|recurse
init|=
literal|false
decl_stmt|;
name|boolean
name|exclude
init|=
literal|false
decl_stmt|;
name|String
name|name
decl_stmt|;
comment|// Parse command line arguments.
for|for
control|(
specifier|final
name|String
name|arg
range|:
name|argv
control|)
block|{
if|if
condition|(
name|arg
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'-'
condition|)
block|{
comment|// command line switch
if|if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-constants"
argument_list|)
condition|)
block|{
name|constants
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-code"
argument_list|)
condition|)
block|{
name|code
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-brief"
argument_list|)
condition|)
block|{
name|verbose
operator|=
literal|false
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-dependencies"
argument_list|)
condition|)
block|{
name|classdep
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-nocontents"
argument_list|)
condition|)
block|{
name|nocontents
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-recurse"
argument_list|)
condition|)
block|{
name|recurse
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-exclude"
argument_list|)
condition|)
block|{
name|exclude
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|arg
operator|.
name|equals
argument_list|(
literal|"-help"
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Usage: java listclass [-constants] [-code] [-brief] "
operator|+
literal|"[-dependencies] [-nocontents] [-recurse] class... "
operator|+
literal|"[-exclude<list>]\n"
operator|+
literal|"-constants       Print constants table (constant pool)\n"
operator|+
literal|"-code            Dump byte code of methods\n"
operator|+
literal|"-brief           Brief listing\n"
operator|+
literal|"-dependencies    Show class dependencies\n"
operator|+
literal|"-nocontents      Do not print field/method information\n"
operator|+
literal|"-recurse         Recurse into dependent classes\n"
operator|+
literal|"-exclude<list>  Do not list classes beginning with "
operator|+
literal|"strings in<list>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Unknown switch "
operator|+
name|arg
operator|+
literal|" ignored."
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|exclude
condition|)
block|{
comment|// add file name to list
name|excludeName
operator|.
name|add
argument_list|(
name|arg
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fileName
operator|.
name|add
argument_list|(
name|arg
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|fileName
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
literal|"list: No input files specified"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|ListClass
name|listClass
init|=
operator|new
name|ListClass
argument_list|(
name|code
argument_list|,
name|constants
argument_list|,
name|verbose
argument_list|,
name|classdep
argument_list|,
name|nocontents
argument_list|,
name|recurse
argument_list|,
name|excludeName
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|element
range|:
name|fileName
control|)
block|{
name|name
operator|=
name|element
expr_stmt|;
name|listClass
operator|.
name|list
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Dump the list of classes this class is dependent on      */
specifier|public
specifier|static
name|void
name|printClassDependencies
parameter_list|(
specifier|final
name|ConstantPool
name|pool
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Dependencies:"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|String
name|name
range|:
name|getClassDependencies
argument_list|(
name|pool
argument_list|)
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\t"
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Dump the disassembled code of all methods in the class.      */
specifier|public
specifier|static
name|void
name|printCode
parameter_list|(
specifier|final
name|Method
index|[]
name|methods
parameter_list|,
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
for|for
control|(
specifier|final
name|Method
name|method
range|:
name|methods
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|method
argument_list|)
expr_stmt|;
specifier|final
name|Code
name|code
init|=
name|method
operator|.
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|code
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|boolean
name|code
decl_stmt|;
name|boolean
name|constants
decl_stmt|;
name|boolean
name|verbose
decl_stmt|;
name|boolean
name|classDep
decl_stmt|;
name|boolean
name|noContents
decl_stmt|;
name|boolean
name|recurse
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|listedClasses
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|excludeName
decl_stmt|;
specifier|public
name|ListClass
parameter_list|(
specifier|final
name|boolean
name|code
parameter_list|,
specifier|final
name|boolean
name|constants
parameter_list|,
specifier|final
name|boolean
name|verbose
parameter_list|,
specifier|final
name|boolean
name|classDep
parameter_list|,
specifier|final
name|boolean
name|noContents
parameter_list|,
specifier|final
name|boolean
name|recurse
parameter_list|,
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|excludeName
parameter_list|)
block|{
name|this
operator|.
name|code
operator|=
name|code
expr_stmt|;
name|this
operator|.
name|constants
operator|=
name|constants
expr_stmt|;
name|this
operator|.
name|verbose
operator|=
name|verbose
expr_stmt|;
name|this
operator|.
name|classDep
operator|=
name|classDep
expr_stmt|;
name|this
operator|.
name|noContents
operator|=
name|noContents
expr_stmt|;
name|this
operator|.
name|recurse
operator|=
name|recurse
expr_stmt|;
name|this
operator|.
name|listedClasses
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|excludeName
operator|=
name|excludeName
expr_stmt|;
block|}
comment|/**      * Print the given class on screen      */
specifier|public
name|void
name|list
parameter_list|(
specifier|final
name|String
name|name
parameter_list|)
block|{
try|try
block|{
name|JavaClass
name|javaClass
decl_stmt|;
if|if
condition|(
name|listedClasses
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
operator|||
name|name
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
block|{
return|return;
block|}
for|for
control|(
specifier|final
name|String
name|element
range|:
name|excludeName
control|)
block|{
if|if
condition|(
name|name
operator|.
name|startsWith
argument_list|(
name|element
argument_list|)
condition|)
block|{
return|return;
block|}
block|}
if|if
condition|(
name|name
operator|.
name|endsWith
argument_list|(
name|JavaClass
operator|.
name|EXTENSION
argument_list|)
condition|)
block|{
name|javaClass
operator|=
operator|new
name|ClassParser
argument_list|(
name|name
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
comment|// May throw IOException
block|}
else|else
block|{
name|javaClass
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|noContents
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|javaClass
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|javaClass
argument_list|)
expr_stmt|;
comment|// Dump the contents
block|}
if|if
condition|(
name|constants
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|javaClass
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|code
condition|)
block|{
name|printCode
argument_list|(
name|javaClass
operator|.
name|getMethods
argument_list|()
argument_list|,
name|verbose
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|classDep
condition|)
block|{
name|printClassDependencies
argument_list|(
name|javaClass
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|listedClasses
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|recurse
condition|)
block|{
specifier|final
name|String
index|[]
name|dependencies
init|=
name|getClassDependencies
argument_list|(
name|javaClass
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|dependency
range|:
name|dependencies
control|)
block|{
name|list
argument_list|(
name|dependency
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error loading class "
operator|+
name|name
operator|+
literal|" ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error processing class "
operator|+
name|name
operator|+
literal|" ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

