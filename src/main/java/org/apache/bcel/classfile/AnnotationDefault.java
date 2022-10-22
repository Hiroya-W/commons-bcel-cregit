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
name|Const
import|;
end_import

begin_comment
comment|/**  * Represents the default value of a annotation for a method info  *  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|AnnotationDefault
extends|extends
name|Attribute
block|{
specifier|private
name|ElementValue
name|defaultValue
decl_stmt|;
comment|/**      * @param nameIndex Index pointing to the name<em>Code</em>      * @param length Content length in bytes      * @param input Input stream      * @param constantPool Array of constants      */
name|AnnotationDefault
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
name|ElementValue
operator|)
literal|null
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
name|defaultValue
operator|=
name|ElementValue
operator|.
name|readElementValue
argument_list|(
name|input
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param nameIndex Index pointing to the name<em>Code</em>      * @param length Content length in bytes      * @param defaultValue the annotation's default value      * @param constantPool Array of constants      */
specifier|public
name|AnnotationDefault
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
name|ElementValue
name|defaultValue
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
name|ATTR_ANNOTATION_DEFAULT
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
name|defaultValue
operator|=
name|defaultValue
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
name|visitAnnotationDefault
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Attribute
name|copy
parameter_list|(
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
return|return
operator|(
name|Attribute
operator|)
name|clone
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
name|defaultValue
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return the default value      */
specifier|public
specifier|final
name|ElementValue
name|getDefaultValue
parameter_list|()
block|{
return|return
name|defaultValue
return|;
block|}
comment|/**      * @param defaultValue the default value of this methodinfo's annotation      */
specifier|public
specifier|final
name|void
name|setDefaultValue
parameter_list|(
specifier|final
name|ElementValue
name|defaultValue
parameter_list|)
block|{
name|this
operator|.
name|defaultValue
operator|=
name|defaultValue
expr_stmt|;
block|}
block|}
end_class

end_unit

