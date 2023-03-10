begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
name|DataInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
operator|.
name|Args
import|;
end_import

begin_comment
comment|/**  * This class represents a (PC offset, line number) pair, i.e., a line number in the source that corresponds to a  * relative address in the byte code. This is used for debugging purposes.  *  * @see LineNumberTable  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|LineNumber
implements|implements
name|Cloneable
implements|,
name|Node
block|{
specifier|static
specifier|final
name|LineNumber
index|[]
name|EMPTY_ARRAY
init|=
block|{}
decl_stmt|;
comment|/** Program Counter (PC) corresponds to line */
specifier|private
name|int
name|startPc
decl_stmt|;
comment|/** number in source file */
specifier|private
name|int
name|lineNumber
decl_stmt|;
comment|/**      * Construct object from file stream.      *      * @param file Input stream      * @throws IOException if an I/O Exception occurs in readUnsignedShort      */
name|LineNumber
parameter_list|(
specifier|final
name|DataInput
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param startPc Program Counter (PC) corresponds to      * @param lineNumber line number in source file      */
specifier|public
name|LineNumber
parameter_list|(
specifier|final
name|int
name|startPc
parameter_list|,
specifier|final
name|int
name|lineNumber
parameter_list|)
block|{
name|this
operator|.
name|startPc
operator|=
name|Args
operator|.
name|requireU2
argument_list|(
name|startPc
argument_list|,
literal|"startPc"
argument_list|)
expr_stmt|;
name|this
operator|.
name|lineNumber
operator|=
name|Args
operator|.
name|requireU2
argument_list|(
name|lineNumber
argument_list|,
literal|"lineNumber"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize from another object.      *      * @param c the object to copy      */
specifier|public
name|LineNumber
parameter_list|(
specifier|final
name|LineNumber
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getStartPC
argument_list|()
argument_list|,
name|c
operator|.
name|getLineNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
specifier|final
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitLineNumber
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|LineNumber
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|LineNumber
operator|)
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
comment|// TODO should this throw?
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Dump line number/pc pair to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O Exception occurs in writeShort      */
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|startPc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|lineNumber
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Corresponding source line      */
specifier|public
name|int
name|getLineNumber
parameter_list|()
block|{
return|return
name|lineNumber
operator|&
literal|0xffff
return|;
block|}
comment|/**      * @return PC in code      */
specifier|public
name|int
name|getStartPC
parameter_list|()
block|{
return|return
name|startPc
operator|&
literal|0xffff
return|;
block|}
comment|/**      * @param lineNumber the source line number      */
specifier|public
name|void
name|setLineNumber
parameter_list|(
specifier|final
name|int
name|lineNumber
parameter_list|)
block|{
name|this
operator|.
name|lineNumber
operator|=
operator|(
name|short
operator|)
name|lineNumber
expr_stmt|;
block|}
comment|/**      * @param startPc the pc for this line number      */
specifier|public
name|void
name|setStartPC
parameter_list|(
specifier|final
name|int
name|startPc
parameter_list|)
block|{
name|this
operator|.
name|startPc
operator|=
operator|(
name|short
operator|)
name|startPc
expr_stmt|;
block|}
comment|/**      * @return String representation      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"LineNumber("
operator|+
name|getStartPC
argument_list|()
operator|+
literal|", "
operator|+
name|getLineNumber
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

