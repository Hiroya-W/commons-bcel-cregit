begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|javax
operator|.
name|imageio
operator|.
name|stream
operator|.
name|FileImageInputStream
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
name|Attribute
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
name|ClassFormatException
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
name|Field
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
name|BCELifier
import|;
end_import

begin_comment
comment|/**  * Display Java .class file data. Output is based on javap tool. Built using the BCEL libary.  *  */
end_comment

begin_class
class|class
name|ClassDumper
block|{
specifier|private
specifier|final
name|FileImageInputStream
name|file
decl_stmt|;
specifier|private
specifier|final
name|String
name|file_name
decl_stmt|;
specifier|private
name|int
name|classNameIndex
decl_stmt|;
specifier|private
name|int
name|superclassNameIndex
decl_stmt|;
specifier|private
name|int
name|major
decl_stmt|;
specifier|private
name|int
name|minor
decl_stmt|;
comment|// Compiler version
specifier|private
name|int
name|accessFlags
decl_stmt|;
comment|// Access rights of parsed class
specifier|private
name|int
index|[]
name|interfaces
decl_stmt|;
comment|// Names of implemented interfaces
specifier|private
name|ConstantPool
name|constantPool
decl_stmt|;
comment|// collection of constants
specifier|private
name|Constant
index|[]
name|constantItems
decl_stmt|;
comment|// collection of constants
specifier|private
name|Field
index|[]
name|fields
decl_stmt|;
comment|// class fields, i.e., its variables
specifier|private
name|Method
index|[]
name|methods
decl_stmt|;
comment|// methods defined in the class
specifier|private
name|Attribute
index|[]
name|attributes
decl_stmt|;
comment|// attributes defined in the class
comment|/**      * Parses class from the given stream.      *      * @param file Input stream      * @param file_name File name      */
specifier|public
name|ClassDumper
parameter_list|(
specifier|final
name|FileImageInputStream
name|file
parameter_list|,
specifier|final
name|String
name|file_name
parameter_list|)
block|{
name|this
operator|.
name|file_name
operator|=
name|file_name
expr_stmt|;
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
block|}
specifier|private
specifier|final
name|String
name|constantToString
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
specifier|final
name|Constant
name|c
init|=
name|constantItems
index|[
name|index
index|]
decl_stmt|;
return|return
name|constantPool
operator|.
name|constantToString
argument_list|(
name|c
argument_list|)
return|;
block|}
comment|/**      * Parses the given Java class file and return an object that represents the contained data, i.e., constants, methods,      * fields and commands. A<em>ClassFormatException</em> is raised, if the file is not a valid .class file. (This does      * not include verification of the byte code as it is performed by the java interpreter).      *      * @throws IOException      * @throws ClassFormatException      */
specifier|public
name|void
name|dump
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
try|try
block|{
comment|// Check magic tag of class file
name|processID
argument_list|()
expr_stmt|;
comment|// Get compiler version
name|processVersion
argument_list|()
expr_stmt|;
comment|// process constant pool entries
name|processConstantPool
argument_list|()
expr_stmt|;
comment|// Get class information
name|processClassInfo
argument_list|()
expr_stmt|;
comment|// Get interface information, i.e., implemented interfaces
name|processInterfaces
argument_list|()
expr_stmt|;
comment|// process class fields, i.e., the variables of the class
name|processFields
argument_list|()
expr_stmt|;
comment|// process class methods, i.e., the functions in the class
name|processMethods
argument_list|()
expr_stmt|;
comment|// process class attributes
name|processAttributes
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
comment|// Processed everything of interest, so close the file
try|try
block|{
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
block|{
name|file
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|IOException
name|ioe
parameter_list|)
block|{
comment|// ignore close exceptions
block|}
block|}
block|}
comment|/**      * Processes information about the attributes of the class.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processAttributes
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|int
name|attributes_count
decl_stmt|;
name|attributes_count
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|attributes
operator|=
operator|new
name|Attribute
index|[
name|attributes_count
index|]
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nAttributes(%d):%n"
argument_list|,
name|attributes_count
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|attributes_count
condition|;
name|i
operator|++
control|)
block|{
name|attributes
index|[
name|i
index|]
operator|=
name|Attribute
operator|.
name|readAttribute
argument_list|(
name|file
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
comment|// indent all lines by two spaces
specifier|final
name|String
index|[]
name|lines
init|=
name|attributes
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\\r?\\n"
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|line
range|:
name|lines
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  "
operator|+
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Processes information about the class and its super class.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processClassInfo
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|accessFlags
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
comment|/*          * Interfaces are implicitely abstract, the flag should be set according to the JVM specification.          */
if|if
condition|(
operator|(
name|accessFlags
operator|&
name|Const
operator|.
name|ACC_INTERFACE
operator|)
operator|!=
literal|0
condition|)
block|{
name|accessFlags
operator||=
name|Const
operator|.
name|ACC_ABSTRACT
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|accessFlags
operator|&
name|Const
operator|.
name|ACC_ABSTRACT
operator|)
operator|!=
literal|0
operator|&&
operator|(
name|accessFlags
operator|&
name|Const
operator|.
name|ACC_FINAL
operator|)
operator|!=
literal|0
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Class "
operator|+
name|file_name
operator|+
literal|" can't be both final and abstract"
argument_list|)
throw|;
block|}
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nClass info:%n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  flags: "
operator|+
name|BCELifier
operator|.
name|printFlags
argument_list|(
name|accessFlags
argument_list|,
name|BCELifier
operator|.
name|FLAGS
operator|.
name|CLASS
argument_list|)
argument_list|)
expr_stmt|;
name|classNameIndex
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  this_class: %d ("
argument_list|,
name|classNameIndex
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|constantToString
argument_list|(
name|classNameIndex
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|superclassNameIndex
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  super_class: %d ("
argument_list|,
name|superclassNameIndex
argument_list|)
expr_stmt|;
if|if
condition|(
name|superclassNameIndex
operator|>
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%s"
argument_list|,
name|constantToString
argument_list|(
name|superclassNameIndex
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Processes constant pool entries.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processConstantPool
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|byte
name|tag
decl_stmt|;
specifier|final
name|int
name|constant_pool_count
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|constantItems
operator|=
operator|new
name|Constant
index|[
name|constant_pool_count
index|]
expr_stmt|;
name|constantPool
operator|=
operator|new
name|ConstantPool
argument_list|(
name|constantItems
argument_list|)
expr_stmt|;
comment|// constantPool[0] is unused by the compiler
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nConstant pool(%d):%n"
argument_list|,
name|constant_pool_count
operator|-
literal|1
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|constant_pool_count
condition|;
name|i
operator|++
control|)
block|{
name|constantItems
index|[
name|i
index|]
operator|=
name|Constant
operator|.
name|readConstant
argument_list|(
name|file
argument_list|)
expr_stmt|;
comment|// i'm sure there is a better way to do this
if|if
condition|(
name|i
operator|<
literal|10
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"    #%1d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|i
operator|<
literal|100
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"   #%2d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  #%d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|constantItems
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// All eight byte constants take up two spots in the constant pool
name|tag
operator|=
name|constantItems
index|[
name|i
index|]
operator|.
name|getTag
argument_list|()
expr_stmt|;
if|if
condition|(
name|tag
operator|==
name|Const
operator|.
name|CONSTANT_Double
operator|||
name|tag
operator|==
name|Const
operator|.
name|CONSTANT_Long
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Constructs object from file stream.      *      * @param file Input stream      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processFieldOrMethod
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
specifier|final
name|int
name|access_flags
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
specifier|final
name|int
name|name_index
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  name_index: %d ("
argument_list|,
name|name_index
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|constantToString
argument_list|(
name|name_index
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  accessFlags: "
operator|+
name|BCELifier
operator|.
name|printFlags
argument_list|(
name|access_flags
argument_list|,
name|BCELifier
operator|.
name|FLAGS
operator|.
name|METHOD
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|int
name|descriptor_index
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  descriptor_index: %d ("
argument_list|,
name|descriptor_index
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|constantToString
argument_list|(
name|descriptor_index
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
specifier|final
name|int
name|attributes_count
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
specifier|final
name|Attribute
index|[]
name|attributes
init|=
operator|new
name|Attribute
index|[
name|attributes_count
index|]
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  attribute count: "
operator|+
name|attributes_count
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|attributes_count
condition|;
name|i
operator|++
control|)
block|{
comment|// going to peek ahead a bit
name|file
operator|.
name|mark
argument_list|()
expr_stmt|;
specifier|final
name|int
name|attribute_name_index
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
specifier|final
name|int
name|attribute_length
init|=
name|file
operator|.
name|readInt
argument_list|()
decl_stmt|;
comment|// restore file location
name|file
operator|.
name|reset
argument_list|()
expr_stmt|;
comment|// Usefull for debugging
comment|// System.out.printf(" attribute_name_index: %d (", attribute_name_index);
comment|// System.out.println(constantToString(attribute_name_index) + ")");
comment|// System.out.printf(" atribute offset in file: %x%n", + file.getStreamPosition());
comment|// System.out.println(" atribute_length: " + attribute_length);
comment|// A stronger verification test would be to read attribute_length bytes
comment|// into a buffer. Then pass that buffer to readAttribute and also
comment|// verify we're at EOF of the buffer on return.
specifier|final
name|long
name|pos1
init|=
name|file
operator|.
name|getStreamPosition
argument_list|()
decl_stmt|;
name|attributes
index|[
name|i
index|]
operator|=
name|Attribute
operator|.
name|readAttribute
argument_list|(
name|file
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
specifier|final
name|long
name|pos2
init|=
name|file
operator|.
name|getStreamPosition
argument_list|()
decl_stmt|;
if|if
condition|(
name|pos2
operator|-
name|pos1
operator|!=
name|attribute_length
operator|+
literal|6
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nattribute_length: %d pos2-pos1-6: %d pos1: %x(%d) pos2: %x(%d)%n"
argument_list|,
name|attribute_length
argument_list|,
name|pos2
operator|-
name|pos1
operator|-
literal|6
argument_list|,
name|pos1
argument_list|,
name|pos1
argument_list|,
name|pos2
argument_list|,
name|pos2
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  "
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|attributes
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Processes information about the fields of the class, i.e., its variables.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processFields
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|int
name|fields_count
decl_stmt|;
name|fields_count
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|fields
operator|=
operator|new
name|Field
index|[
name|fields_count
index|]
expr_stmt|;
comment|// sometimes fields[0] is magic used for serialization
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nFields(%d):%n"
argument_list|,
name|fields_count
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields_count
condition|;
name|i
operator|++
control|)
block|{
name|processFieldOrMethod
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|fields_count
operator|-
literal|1
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Checks whether the header of the file is ok. Of course, this has to be the first action on successive file reads.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processID
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
specifier|final
name|int
name|magic
init|=
name|file
operator|.
name|readInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|magic
operator|!=
name|Const
operator|.
name|JVM_CLASSFILE_MAGIC
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
name|file_name
operator|+
literal|" is not a Java .class file"
argument_list|)
throw|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Java Class Dump"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  file: "
operator|+
name|file_name
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nClass header:%n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  magic: %X%n"
argument_list|,
name|magic
argument_list|)
expr_stmt|;
block|}
comment|/**      * Processes information about the interfaces implemented by this class.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processInterfaces
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|int
name|interfaces_count
decl_stmt|;
name|interfaces_count
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|interfaces
operator|=
operator|new
name|int
index|[
name|interfaces_count
index|]
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nInterfaces(%d):%n"
argument_list|,
name|interfaces_count
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|interfaces_count
condition|;
name|i
operator|++
control|)
block|{
name|interfaces
index|[
name|i
index|]
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
comment|// i'm sure there is a better way to do this
if|if
condition|(
name|i
operator|<
literal|10
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"   #%1d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|i
operator|<
literal|100
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  #%2d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|" #%d = "
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|interfaces
index|[
name|i
index|]
operator|+
literal|" ("
operator|+
name|constantPool
operator|.
name|getConstantString
argument_list|(
name|interfaces
index|[
name|i
index|]
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Processes information about the methods of the class.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processMethods
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|int
name|methods_count
decl_stmt|;
name|methods_count
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|methods
operator|=
operator|new
name|Method
index|[
name|methods_count
index|]
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"%nMethods(%d):%n"
argument_list|,
name|methods_count
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|methods_count
condition|;
name|i
operator|++
control|)
block|{
name|processFieldOrMethod
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|methods_count
operator|-
literal|1
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Processes major and minor version of compiler which created the file.      *      * @throws IOException      * @throws ClassFormatException      */
specifier|private
specifier|final
name|void
name|processVersion
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|minor
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  minor version: %s%n"
argument_list|,
name|minor
argument_list|)
expr_stmt|;
name|major
operator|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"  major version: %s%n"
argument_list|,
name|major
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_class
class|class
name|DumpClass
block|{
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|args
operator|.
name|length
operator|!=
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Require file name as only argument"
argument_list|)
throw|;
block|}
try|try
init|(
name|FileImageInputStream
name|file
init|=
operator|new
name|FileImageInputStream
argument_list|(
operator|new
name|File
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|)
init|)
block|{
specifier|final
name|ClassDumper
name|cd
init|=
operator|new
name|ClassDumper
argument_list|(
name|file
argument_list|,
name|args
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|cd
operator|.
name|dump
argument_list|()
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"End of Class Dump%n"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

