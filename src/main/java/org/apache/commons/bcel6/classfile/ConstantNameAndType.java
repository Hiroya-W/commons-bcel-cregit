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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**   * This class is derived from the abstract   *<A HREF="org.apache.commons.bcel6.classfile.Constant.html">Constant</A> class   * and represents a reference to the name and signature  * of a field or method.  *  * @version $Id$  * @see     Constant  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ConstantNameAndType
extends|extends
name|Constant
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7913354727264034451L
decl_stmt|;
specifier|private
name|int
name|name_index
decl_stmt|;
comment|// Name of field/method // TODO could be final (setter unused)
specifier|private
name|int
name|signature_index
decl_stmt|;
comment|// and its signature. // TODO could be final (setter unused)
comment|/**      * Initialize from another object.      */
specifier|public
name|ConstantNameAndType
parameter_list|(
name|ConstantNameAndType
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
name|getSignatureIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize instance from file data.      *      * @param file Input stream      * @throws IOException      */
name|ConstantNameAndType
parameter_list|(
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
comment|/**      * @param name_index Name of field/method      * @param signature_index and its signature      */
specifier|public
name|ConstantNameAndType
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|signature_index
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|CONSTANT_NameAndType
argument_list|)
expr_stmt|;
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitConstantNameAndType
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump name and signature index to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|file
operator|.
name|writeByte
argument_list|(
name|super
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|name_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|signature_index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Name index in constant pool of field/method name.      */
specifier|public
specifier|final
name|int
name|getNameIndex
parameter_list|()
block|{
return|return
name|name_index
return|;
block|}
comment|/** @return name      */
specifier|public
specifier|final
name|String
name|getName
parameter_list|(
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|cp
operator|.
name|constantToString
argument_list|(
name|getNameIndex
argument_list|()
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
return|;
block|}
comment|/**      * @return Index in constant pool of field/method signature.      */
specifier|public
specifier|final
name|int
name|getSignatureIndex
parameter_list|()
block|{
return|return
name|signature_index
return|;
block|}
comment|/** @return signature      */
specifier|public
specifier|final
name|String
name|getSignature
parameter_list|(
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|cp
operator|.
name|constantToString
argument_list|(
name|getSignatureIndex
argument_list|()
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
return|;
block|}
comment|/**      * @param name_index the name index of this constant      */
specifier|public
specifier|final
name|void
name|setNameIndex
parameter_list|(
name|int
name|name_index
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
block|}
comment|/**      * @param signature_index the signature index in the constant pool of this type      */
specifier|public
specifier|final
name|void
name|setSignatureIndex
parameter_list|(
name|int
name|signature_index
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
block|}
comment|/**      * @return String representation      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|"(name_index = "
operator|+
name|name_index
operator|+
literal|", signature_index = "
operator|+
name|signature_index
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

