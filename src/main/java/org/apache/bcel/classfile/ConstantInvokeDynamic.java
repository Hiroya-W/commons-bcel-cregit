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
name|Const
import|;
end_import

begin_comment
comment|/**  * This class is derived from the abstract {@link Constant} and represents a reference to a invoke dynamic.  *  * @see Constant  * @see<a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.10"> The  *      CONSTANT_InvokeDynamic_info Structure in The Java Virtual Machine Specification</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ConstantInvokeDynamic
extends|extends
name|ConstantCP
block|{
comment|/**      * Initialize from another object.      */
specifier|public
name|ConstantInvokeDynamic
parameter_list|(
specifier|final
name|ConstantInvokeDynamic
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getBootstrapMethodAttrIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize instance from file data.      *      * @param file Input stream      * @throws IOException if an I/O error occurs.      */
name|ConstantInvokeDynamic
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
name|readShort
argument_list|()
argument_list|,
name|file
operator|.
name|readShort
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ConstantInvokeDynamic
parameter_list|(
specifier|final
name|int
name|bootstrapMethodAttrIndex
parameter_list|,
specifier|final
name|int
name|nameAndTypeIndex
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|CONSTANT_InvokeDynamic
argument_list|,
name|bootstrapMethodAttrIndex
argument_list|,
name|nameAndTypeIndex
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class. I.e.,      * the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitConstantInvokeDynamic
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Reference (index) to bootstrap method this constant refers to.      *      *         Note that this method is a functional duplicate of getClassIndex for use by ConstantInvokeDynamic.      * @since 6.0      */
specifier|public
name|int
name|getBootstrapMethodAttrIndex
parameter_list|()
block|{
return|return
name|super
operator|.
name|getClassIndex
argument_list|()
return|;
comment|// AKA bootstrap_method_attr_index
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
name|super
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"class_index"
argument_list|,
literal|"bootstrap_method_attr_index"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

