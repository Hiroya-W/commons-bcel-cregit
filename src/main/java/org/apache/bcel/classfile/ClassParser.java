begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
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
name|zip
operator|.
name|ZipEntry
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
name|ZipFile
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

begin_comment
comment|/**  * Wrapper class that parses a given Java .class file. The method<a href ="#parse">parse</a> returns a  *<a href ="JavaClass.html"> JavaClass</a> object on success. When an I/O error or an inconsistency occurs an  * appropriate exception is propagated back to the caller.  *  * The structure and the names comply, except for a few conveniences, exactly with the  *<a href="http://docs.oracle.com/javase/specs/"> JVM specification 1.0</a>. See this paper for further details about  * the structure of a bytecode file.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ClassParser
block|{
specifier|private
specifier|static
specifier|final
name|int
name|BUFSIZE
init|=
literal|8192
decl_stmt|;
specifier|private
name|DataInputStream
name|dataInputStream
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|fileOwned
decl_stmt|;
specifier|private
specifier|final
name|String
name|fileName
decl_stmt|;
specifier|private
name|String
name|zipFile
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
comment|// Compiler version
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
specifier|private
specifier|final
name|boolean
name|isZip
decl_stmt|;
comment|// Loaded from zip file
comment|/**      * Parses class from the given stream.      *      * @param inputStream Input stream      * @param fileName File name      */
specifier|public
name|ClassParser
parameter_list|(
specifier|final
name|InputStream
name|inputStream
parameter_list|,
specifier|final
name|String
name|fileName
parameter_list|)
block|{
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
name|this
operator|.
name|fileOwned
operator|=
literal|false
expr_stmt|;
specifier|final
name|String
name|clazz
init|=
name|inputStream
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|// Not a very clean solution ...
name|this
operator|.
name|isZip
operator|=
name|clazz
operator|.
name|startsWith
argument_list|(
literal|"java.util.zip."
argument_list|)
operator|||
name|clazz
operator|.
name|startsWith
argument_list|(
literal|"java.util.jar."
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputStream
operator|instanceof
name|DataInputStream
condition|)
block|{
name|this
operator|.
name|dataInputStream
operator|=
operator|(
name|DataInputStream
operator|)
name|inputStream
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|dataInputStream
operator|=
operator|new
name|DataInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
name|inputStream
argument_list|,
name|BUFSIZE
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Parses class from given .class file.      *      * @param fileName file name      */
specifier|public
name|ClassParser
parameter_list|(
specifier|final
name|String
name|fileName
parameter_list|)
block|{
name|this
operator|.
name|isZip
operator|=
literal|false
expr_stmt|;
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
name|this
operator|.
name|fileOwned
operator|=
literal|true
expr_stmt|;
block|}
comment|/**      * Parses class from given .class file in a ZIP-archive      *      * @param zipFile zip file name      * @param fileName file name      */
specifier|public
name|ClassParser
parameter_list|(
specifier|final
name|String
name|zipFile
parameter_list|,
specifier|final
name|String
name|fileName
parameter_list|)
block|{
name|this
operator|.
name|isZip
operator|=
literal|true
expr_stmt|;
name|this
operator|.
name|fileOwned
operator|=
literal|true
expr_stmt|;
name|this
operator|.
name|zipFile
operator|=
name|zipFile
expr_stmt|;
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
block|}
comment|/**      * Parses the given Java class file and return an object that represents the contained data, i.e., constants, methods,      * fields and commands. A<em>ClassFormatException</em> is raised, if the file is not a valid .class file. (This does      * not include verification of the byte code as it is performed by the java interpreter).      *      * @return Class object representing the parsed class file      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|public
name|JavaClass
name|parse
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|ZipFile
name|zip
init|=
literal|null
decl_stmt|;
try|try
block|{
if|if
condition|(
name|fileOwned
condition|)
block|{
if|if
condition|(
name|isZip
condition|)
block|{
name|zip
operator|=
operator|new
name|ZipFile
argument_list|(
name|zipFile
argument_list|)
expr_stmt|;
specifier|final
name|ZipEntry
name|entry
init|=
name|zip
operator|.
name|getEntry
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File "
operator|+
name|fileName
operator|+
literal|" not found"
argument_list|)
throw|;
block|}
name|dataInputStream
operator|=
operator|new
name|DataInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
name|zip
operator|.
name|getInputStream
argument_list|(
name|entry
argument_list|)
argument_list|,
name|BUFSIZE
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dataInputStream
operator|=
operator|new
name|DataInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|BUFSIZE
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/****************** Read headers ********************************/
comment|// Check magic tag of class file
name|readID
argument_list|()
expr_stmt|;
comment|// Get compiler version
name|readVersion
argument_list|()
expr_stmt|;
comment|/****************** Read constant pool and related **************/
comment|// Read constant pool entries
name|readConstantPool
argument_list|()
expr_stmt|;
comment|// Get class information
name|readClassInfo
argument_list|()
expr_stmt|;
comment|// Get interface information, i.e., implemented interfaces
name|readInterfaces
argument_list|()
expr_stmt|;
comment|/****************** Read class fields and methods ***************/
comment|// Read class fields, i.e., the variables of the class
name|readFields
argument_list|()
expr_stmt|;
comment|// Read class methods, i.e., the functions in the class
name|readMethods
argument_list|()
expr_stmt|;
comment|// Read class attributes
name|readAttributes
argument_list|()
expr_stmt|;
comment|// Check for unknown variables
comment|// Unknown[] u = Unknown.getUnknownAttributes();
comment|// for (int i=0; i< u.length; i++)
comment|// System.err.println("WARNING: " + u[i]);
comment|// Everything should have been read now
comment|// if(file.available()> 0) {
comment|// int bytes = file.available();
comment|// byte[] buf = new byte[bytes];
comment|// file.read(buf);
comment|// if(!(isZip&& (buf.length == 1))) {
comment|// System.err.println("WARNING: Trailing garbage at end of " + fileName);
comment|// System.err.println(bytes + " extra bytes: " + Utility.toHexString(buf));
comment|// }
comment|// }
block|}
finally|finally
block|{
comment|// Read everything of interest, so close the file
if|if
condition|(
name|fileOwned
condition|)
block|{
try|try
block|{
if|if
condition|(
name|dataInputStream
operator|!=
literal|null
condition|)
block|{
name|dataInputStream
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
try|try
block|{
if|if
condition|(
name|zip
operator|!=
literal|null
condition|)
block|{
name|zip
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
comment|// Return the information we have gathered in a new object
return|return
operator|new
name|JavaClass
argument_list|(
name|classNameIndex
argument_list|,
name|superclassNameIndex
argument_list|,
name|fileName
argument_list|,
name|major
argument_list|,
name|minor
argument_list|,
name|accessFlags
argument_list|,
name|constantPool
argument_list|,
name|interfaces
argument_list|,
name|fields
argument_list|,
name|methods
argument_list|,
name|attributes
argument_list|,
name|isZip
condition|?
name|JavaClass
operator|.
name|ZIP
else|:
name|JavaClass
operator|.
name|FILE
argument_list|)
return|;
block|}
comment|/**      * Reads information about the attributes of the class.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readAttributes
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
specifier|final
name|int
name|attributes_count
init|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|attributes
operator|=
operator|new
name|Attribute
index|[
name|attributes_count
index|]
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
name|dataInputStream
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Reads information about the class and its super class.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readClassInfo
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|accessFlags
operator|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
comment|/*          * Interfaces are implicitly abstract, the flag should be set according to the JVM specification.          */
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
name|fileName
operator|+
literal|" can't be both final and abstract"
argument_list|)
throw|;
block|}
name|classNameIndex
operator|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|superclassNameIndex
operator|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
comment|/**      * Reads constant pool entries.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readConstantPool
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|constantPool
operator|=
operator|new
name|ConstantPool
argument_list|(
name|dataInputStream
argument_list|)
expr_stmt|;
block|}
comment|/**      * Reads information about the fields of the class, i.e., its variables.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readFields
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
specifier|final
name|int
name|fields_count
init|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|fields
operator|=
operator|new
name|Field
index|[
name|fields_count
index|]
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
name|fields
index|[
name|i
index|]
operator|=
operator|new
name|Field
argument_list|(
name|dataInputStream
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
block|}
comment|/******************** Private utility methods **********************/
comment|/**      * Checks whether the header of the file is ok. Of course, this has to be the first action on successive file reads.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readID
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
if|if
condition|(
name|dataInputStream
operator|.
name|readInt
argument_list|()
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
name|fileName
operator|+
literal|" is not a Java .class file"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Reads information about the interfaces implemented by this class.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readInterfaces
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
specifier|final
name|int
name|interfaces_count
init|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|interfaces
operator|=
operator|new
name|int
index|[
name|interfaces_count
index|]
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
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Reads information about the methods of the class.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readMethods
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|int
name|methods_count
init|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|methods
operator|=
operator|new
name|Method
index|[
name|methods_count
index|]
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
name|methods
index|[
name|i
index|]
operator|=
operator|new
name|Method
argument_list|(
name|dataInputStream
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Reads major and minor version of compiler which created the file.      *      * @throws IOException if an I/O error occurs.      * @throws ClassFormatException if a class is malformed or cannot be interpreted as a class file      */
specifier|private
name|void
name|readVersion
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassFormatException
block|{
name|minor
operator|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|major
operator|=
name|dataInputStream
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

