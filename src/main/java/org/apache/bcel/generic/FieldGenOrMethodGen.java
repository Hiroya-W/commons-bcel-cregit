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
name|generic
package|;
end_package

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
name|AccessFlags
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

begin_comment
comment|/**  * Super class for FieldGen and MethodGen objects, since they have some methods in common!  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|FieldGenOrMethodGen
extends|extends
name|AccessFlags
implements|implements
name|NamedAndTyped
implements|,
name|Cloneable
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|String
name|name
decl_stmt|;
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|Type
name|type
decl_stmt|;
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|ConstantPoolGen
name|cp
decl_stmt|;
specifier|private
specifier|final
name|List
argument_list|<
name|Attribute
argument_list|>
name|attributeList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// @since 6.0
specifier|private
specifier|final
name|List
argument_list|<
name|AnnotationEntryGen
argument_list|>
name|annotationList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
specifier|protected
name|FieldGenOrMethodGen
parameter_list|()
block|{
block|}
comment|/**      * @since 6.0      */
specifier|protected
name|FieldGenOrMethodGen
parameter_list|(
specifier|final
name|int
name|accessFlags
parameter_list|)
block|{
comment|// TODO could this be package protected?
name|super
argument_list|(
name|accessFlags
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|addAll
parameter_list|(
specifier|final
name|Attribute
index|[]
name|attrs
parameter_list|)
block|{
name|Collections
operator|.
name|addAll
argument_list|(
name|attributeList
argument_list|,
name|attrs
argument_list|)
expr_stmt|;
block|}
comment|/**      * @since 6.0      */
specifier|public
name|void
name|addAnnotationEntry
parameter_list|(
specifier|final
name|AnnotationEntryGen
name|ag
parameter_list|)
block|{
name|annotationList
operator|.
name|add
argument_list|(
name|ag
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add an attribute to this method. Currently, the JVM knows about the 'Code', 'ConstantValue', 'Synthetic' and      * 'Exceptions' attributes. Other attributes will be ignored by the JVM but do no harm.      *      * @param a attribute to be added      */
specifier|public
name|void
name|addAttribute
parameter_list|(
specifier|final
name|Attribute
name|a
parameter_list|)
block|{
name|attributeList
operator|.
name|add
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|clone
parameter_list|()
block|{
try|try
block|{
return|return
name|super
operator|.
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
throw|throw
operator|new
name|Error
argument_list|(
literal|"Clone Not Supported"
argument_list|)
throw|;
comment|// never happens
block|}
block|}
specifier|public
name|AnnotationEntryGen
index|[]
name|getAnnotationEntries
parameter_list|()
block|{
return|return
name|annotationList
operator|.
name|toArray
argument_list|(
name|AnnotationEntryGen
operator|.
name|EMPTY_ARRAY
argument_list|)
return|;
block|}
comment|/**      * @return all attributes of this method.      */
specifier|public
name|Attribute
index|[]
name|getAttributes
parameter_list|()
block|{
return|return
name|attributeList
operator|.
name|toArray
argument_list|(
name|Attribute
operator|.
name|EMPTY_ARRAY
argument_list|)
return|;
block|}
specifier|public
name|ConstantPoolGen
name|getConstantPool
parameter_list|()
block|{
return|return
name|cp
return|;
block|}
comment|/**      * @return name of method/field.      */
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * @return signature of method/field.      */
specifier|public
specifier|abstract
name|String
name|getSignature
parameter_list|()
function_decl|;
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/**      * @since 6.0      */
specifier|public
name|void
name|removeAnnotationEntries
parameter_list|()
block|{
name|annotationList
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|/**      * @since 6.0      */
specifier|public
name|void
name|removeAnnotationEntry
parameter_list|(
specifier|final
name|AnnotationEntryGen
name|ag
parameter_list|)
block|{
name|annotationList
operator|.
name|remove
argument_list|(
name|ag
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove an attribute.      */
specifier|public
name|void
name|removeAttribute
parameter_list|(
specifier|final
name|Attribute
name|a
parameter_list|)
block|{
name|attributeList
operator|.
name|remove
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove all attributes.      */
specifier|public
name|void
name|removeAttributes
parameter_list|()
block|{
name|attributeList
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setConstantPool
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|this
operator|.
name|cp
operator|=
name|cp
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setName
parameter_list|(
specifier|final
name|String
name|name
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setType
parameter_list|(
specifier|final
name|Type
name|type
parameter_list|)
block|{
comment|// TODO could be package-protected?
if|if
condition|(
name|type
operator|.
name|getType
argument_list|()
operator|==
name|Const
operator|.
name|T_ADDRESS
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Type can not be "
operator|+
name|type
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
block|}
end_class

end_unit

