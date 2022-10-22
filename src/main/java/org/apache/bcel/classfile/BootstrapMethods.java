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
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
comment|/**  * This class represents a BootstrapMethods attribute.  *  * @see<a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.23"> The class File Format :  *      The BootstrapMethods Attribute</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|BootstrapMethods
extends|extends
name|Attribute
implements|implements
name|Iterable
argument_list|<
name|BootstrapMethod
argument_list|>
block|{
specifier|private
name|BootstrapMethod
index|[]
name|bootstrapMethods
decl_stmt|;
comment|// TODO this could be made final (setter is not used)
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a      * physical copy.      */
specifier|public
name|BootstrapMethods
parameter_list|(
specifier|final
name|BootstrapMethods
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getLength
argument_list|()
argument_list|,
name|c
operator|.
name|getBootstrapMethods
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param nameIndex Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param bootstrapMethods array of bootstrap methods      * @param constantPool Array of constants      */
specifier|public
name|BootstrapMethods
parameter_list|(
specifier|final
name|int
name|nameIndex
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|BootstrapMethod
index|[]
name|bootstrapMethods
parameter_list|,
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|ATTR_BOOTSTRAP_METHODS
argument_list|,
name|nameIndex
argument_list|,
name|length
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
name|this
operator|.
name|bootstrapMethods
operator|=
name|bootstrapMethods
expr_stmt|;
block|}
comment|/**      * Construct object from Input stream.      *      * @param nameIndex Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param input Input stream      * @param constantPool Array of constants      * @throws IOException if an I/O error occurs.      */
name|BootstrapMethods
parameter_list|(
specifier|final
name|int
name|nameIndex
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|nameIndex
argument_list|,
name|length
argument_list|,
operator|(
name|BootstrapMethod
index|[]
operator|)
literal|null
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
specifier|final
name|int
name|numBootstrapMethods
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|bootstrapMethods
operator|=
operator|new
name|BootstrapMethod
index|[
name|numBootstrapMethods
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
name|numBootstrapMethods
condition|;
name|i
operator|++
control|)
block|{
name|bootstrapMethods
index|[
name|i
index|]
operator|=
operator|new
name|BootstrapMethod
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param v Visitor object      */
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
name|visitBootstrapMethods
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return deep copy of this attribute      */
annotation|@
name|Override
specifier|public
name|BootstrapMethods
name|copy
parameter_list|(
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
specifier|final
name|BootstrapMethods
name|c
init|=
operator|(
name|BootstrapMethods
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|bootstrapMethods
operator|=
operator|new
name|BootstrapMethod
index|[
name|bootstrapMethods
operator|.
name|length
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
name|bootstrapMethods
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|.
name|bootstrapMethods
index|[
name|i
index|]
operator|=
name|bootstrapMethods
index|[
name|i
index|]
operator|.
name|copy
argument_list|()
expr_stmt|;
block|}
name|c
operator|.
name|setConstantPool
argument_list|(
name|constantPool
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
comment|/**      * Dump bootstrap methods attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
annotation|@
name|Override
specifier|public
specifier|final
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
name|super
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|bootstrapMethods
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|BootstrapMethod
name|bootstrapMethod
range|:
name|bootstrapMethods
control|)
block|{
name|bootstrapMethod
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return array of bootstrap method "records"      */
specifier|public
specifier|final
name|BootstrapMethod
index|[]
name|getBootstrapMethods
parameter_list|()
block|{
return|return
name|bootstrapMethods
return|;
block|}
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|BootstrapMethod
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|Stream
operator|.
name|of
argument_list|(
name|bootstrapMethods
argument_list|)
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|/**      * @param bootstrapMethods the array of bootstrap methods      */
specifier|public
specifier|final
name|void
name|setBootstrapMethods
parameter_list|(
specifier|final
name|BootstrapMethod
index|[]
name|bootstrapMethods
parameter_list|)
block|{
name|this
operator|.
name|bootstrapMethods
operator|=
name|bootstrapMethods
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"BootstrapMethods("
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|bootstrapMethods
operator|.
name|length
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"):"
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
name|bootstrapMethods
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
specifier|final
name|int
name|start
init|=
name|buf
operator|.
name|length
argument_list|()
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"  "
argument_list|)
operator|.
name|append
argument_list|(
name|i
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
specifier|final
name|int
name|indentCount
init|=
name|buf
operator|.
name|length
argument_list|()
operator|-
name|start
decl_stmt|;
specifier|final
name|String
index|[]
name|lines
init|=
name|bootstrapMethods
index|[
name|i
index|]
operator|.
name|toString
argument_list|(
name|super
operator|.
name|getConstantPool
argument_list|()
argument_list|)
operator|.
name|split
argument_list|(
literal|"\\r?\\n"
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|lines
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|1
init|;
name|j
operator|<
name|lines
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
operator|.
name|append
argument_list|(
literal|"          "
argument_list|,
literal|0
argument_list|,
name|indentCount
argument_list|)
operator|.
name|append
argument_list|(
name|lines
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

