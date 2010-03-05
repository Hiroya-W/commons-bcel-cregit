begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2009 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
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

begin_class
specifier|public
class|class
name|AnnotationElementValue
extends|extends
name|ElementValue
block|{
comment|// For annotation element values, this is the annotation
specifier|private
name|AnnotationEntry
name|annotationEntry
decl_stmt|;
specifier|public
name|AnnotationElementValue
parameter_list|(
name|int
name|type
parameter_list|,
name|AnnotationEntry
name|annotationEntry
parameter_list|,
name|ConstantPool
name|cpool
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|!=
name|ANNOTATION
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Only element values of type annotation can be built with this ctor - type specified: "
operator|+
name|type
argument_list|)
throw|;
name|this
operator|.
name|annotationEntry
operator|=
name|annotationEntry
expr_stmt|;
block|}
specifier|public
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
block|{
name|dos
operator|.
name|writeByte
argument_list|(
name|type
argument_list|)
expr_stmt|;
comment|// u1 type of value (ANNOTATION == '@')
name|annotationEntry
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|stringifyValue
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|annotationEntry
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|stringifyValue
argument_list|()
return|;
block|}
specifier|public
name|AnnotationEntry
name|getAnnotationEntry
parameter_list|()
block|{
return|return
name|annotationEntry
return|;
block|}
block|}
end_class

end_unit

