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

begin_comment
comment|/**  * represents one parameter annotation in the parameter annotation table  *  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|ParameterAnnotationEntry
implements|implements
name|Node
block|{
specifier|static
specifier|final
name|ParameterAnnotationEntry
index|[]
name|EMPTY_ARRAY
init|=
block|{}
decl_stmt|;
specifier|private
specifier|final
name|AnnotationEntry
index|[]
name|annotationTable
decl_stmt|;
comment|/**      * Construct object from input stream.      *      * @param input Input stream      * @throws IOException      */
name|ParameterAnnotationEntry
parameter_list|(
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|int
name|annotation_table_length
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|annotationTable
operator|=
operator|new
name|AnnotationEntry
index|[
name|annotation_table_length
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
name|annotation_table_length
condition|;
name|i
operator|++
control|)
block|{
comment|// TODO isRuntimeVisible
name|annotationTable
index|[
name|i
index|]
operator|=
name|AnnotationEntry
operator|.
name|read
argument_list|(
name|input
argument_list|,
name|constant_pool
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitParameterAnnotationEntry
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * returns the array of annotation entries in this annotation      */
specifier|public
name|AnnotationEntry
index|[]
name|getAnnotationEntries
parameter_list|()
block|{
return|return
name|annotationTable
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
name|annotationTable
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|AnnotationEntry
name|entry
range|:
name|annotationTable
control|)
block|{
name|entry
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|ParameterAnnotationEntry
index|[]
name|createParameterAnnotationEntries
parameter_list|(
specifier|final
name|Attribute
index|[]
name|attrs
parameter_list|)
block|{
comment|// Find attributes that contain parameter annotation data
specifier|final
name|List
argument_list|<
name|ParameterAnnotationEntry
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
name|ParameterAnnotations
condition|)
block|{
specifier|final
name|ParameterAnnotations
name|runtimeAnnotations
init|=
operator|(
name|ParameterAnnotations
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
name|getParameterAnnotationEntries
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
name|ParameterAnnotationEntry
operator|.
name|EMPTY_ARRAY
argument_list|)
return|;
block|}
block|}
end_class

end_unit

