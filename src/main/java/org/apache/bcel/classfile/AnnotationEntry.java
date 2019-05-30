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

begin_comment
comment|/**  * represents one annotation in the annotation table  *  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|AnnotationEntry
implements|implements
name|Node
block|{
specifier|private
specifier|final
name|int
name|type_index
decl_stmt|;
specifier|private
specifier|final
name|ConstantPool
name|constant_pool
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|isRuntimeVisible
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ElementValuePair
argument_list|>
name|element_value_pairs
decl_stmt|;
comment|/*      * Factory method to create an AnnotionEntry from a DataInput      *      * @param input      * @param constant_pool      * @param isRuntimeVisible      * @return the entry      * @throws IOException      */
specifier|public
specifier|static
name|AnnotationEntry
name|read
parameter_list|(
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|,
specifier|final
name|boolean
name|isRuntimeVisible
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|AnnotationEntry
name|annotationEntry
init|=
operator|new
name|AnnotationEntry
argument_list|(
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|constant_pool
argument_list|,
name|isRuntimeVisible
argument_list|)
decl_stmt|;
specifier|final
name|int
name|num_element_value_pairs
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|annotationEntry
operator|.
name|element_value_pairs
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|num_element_value_pairs
condition|;
name|i
operator|++
control|)
block|{
name|annotationEntry
operator|.
name|element_value_pairs
operator|.
name|add
argument_list|(
operator|new
name|ElementValuePair
argument_list|(
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|ElementValue
operator|.
name|readElementValue
argument_list|(
name|input
argument_list|,
name|constant_pool
argument_list|)
argument_list|,
name|constant_pool
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|annotationEntry
return|;
block|}
specifier|public
name|AnnotationEntry
parameter_list|(
specifier|final
name|int
name|type_index
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|,
specifier|final
name|boolean
name|isRuntimeVisible
parameter_list|)
block|{
name|this
operator|.
name|type_index
operator|=
name|type_index
expr_stmt|;
name|this
operator|.
name|constant_pool
operator|=
name|constant_pool
expr_stmt|;
name|this
operator|.
name|isRuntimeVisible
operator|=
name|isRuntimeVisible
expr_stmt|;
block|}
specifier|public
name|int
name|getTypeIndex
parameter_list|()
block|{
return|return
name|type_index
return|;
block|}
specifier|public
name|ConstantPool
name|getConstantPool
parameter_list|()
block|{
return|return
name|constant_pool
return|;
block|}
specifier|public
name|boolean
name|isRuntimeVisible
parameter_list|()
block|{
return|return
name|isRuntimeVisible
return|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitAnnotationEntry
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return the annotation type name      */
specifier|public
name|String
name|getAnnotationType
parameter_list|()
block|{
specifier|final
name|ConstantUtf8
name|c
init|=
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|type_index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
decl_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return the annotation type index      */
specifier|public
name|int
name|getAnnotationTypeIndex
parameter_list|()
block|{
return|return
name|type_index
return|;
block|}
comment|/**      * @return the number of element value pairs in this annotation entry      */
specifier|public
specifier|final
name|int
name|getNumElementValuePairs
parameter_list|()
block|{
return|return
name|element_value_pairs
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * @return the element value pairs in this annotation entry      */
specifier|public
name|ElementValuePair
index|[]
name|getElementValuePairs
parameter_list|()
block|{
comment|// TODO return List
return|return
name|element_value_pairs
operator|.
name|toArray
argument_list|(
operator|new
name|ElementValuePair
index|[
name|element_value_pairs
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
specifier|public
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
name|dos
operator|.
name|writeShort
argument_list|(
name|type_index
argument_list|)
expr_stmt|;
comment|// u2 index of type name in cpool
name|dos
operator|.
name|writeShort
argument_list|(
name|element_value_pairs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// u2 element_value pair
comment|// count
for|for
control|(
specifier|final
name|ElementValuePair
name|envp
range|:
name|element_value_pairs
control|)
block|{
name|envp
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|addElementNameValuePair
parameter_list|(
specifier|final
name|ElementValuePair
name|elementNameValuePair
parameter_list|)
block|{
name|element_value_pairs
operator|.
name|add
argument_list|(
name|elementNameValuePair
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toShortString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|result
operator|.
name|append
argument_list|(
literal|"@"
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAnnotationType
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|ElementValuePair
index|[]
name|evPairs
init|=
name|getElementValuePairs
argument_list|()
decl_stmt|;
if|if
condition|(
name|evPairs
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|ElementValuePair
name|element
range|:
name|evPairs
control|)
block|{
name|result
operator|.
name|append
argument_list|(
name|element
operator|.
name|toShortString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toShortString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|AnnotationEntry
index|[]
name|createAnnotationEntries
parameter_list|(
specifier|final
name|Attribute
index|[]
name|attrs
parameter_list|)
block|{
comment|// Find attributes that contain annotation data
specifier|final
name|List
argument_list|<
name|AnnotationEntry
argument_list|>
name|accumulatedAnnotations
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|attrs
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attrs
control|)
block|{
if|if
condition|(
name|attribute
operator|instanceof
name|Annotations
condition|)
block|{
specifier|final
name|Annotations
name|runtimeAnnotations
init|=
operator|(
name|Annotations
operator|)
name|attribute
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|accumulatedAnnotations
argument_list|,
name|runtimeAnnotations
operator|.
name|getAnnotationEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|accumulatedAnnotations
operator|.
name|toArray
argument_list|(
operator|new
name|AnnotationEntry
index|[
name|accumulatedAnnotations
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
block|}
end_class

end_unit

