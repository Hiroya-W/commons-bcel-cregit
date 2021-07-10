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

begin_comment
comment|/**  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|ArrayElementValue
extends|extends
name|ElementValue
block|{
comment|// For array types, this is the array
specifier|private
specifier|final
name|ElementValue
index|[]
name|elementValues
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"{"
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
name|elementValues
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|elementValues
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|elementValues
operator|.
name|length
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"}"
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
name|ArrayElementValue
parameter_list|(
specifier|final
name|int
name|type
parameter_list|,
specifier|final
name|ElementValue
index|[]
name|datums
parameter_list|,
specifier|final
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
name|ARRAY
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Only element values of type array can be built with this ctor - type specified: "
operator|+
name|type
argument_list|)
throw|;
block|}
name|this
operator|.
name|elementValues
operator|=
name|datums
expr_stmt|;
block|}
annotation|@
name|Override
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
name|writeByte
argument_list|(
name|super
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
comment|// u1 type of value (ARRAY == '[')
name|dos
operator|.
name|writeShort
argument_list|(
name|elementValues
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|ElementValue
name|evalue
range|:
name|elementValues
control|)
block|{
name|evalue
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|stringifyValue
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"["
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
name|elementValues
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|elementValues
index|[
name|i
index|]
operator|.
name|stringifyValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|elementValues
operator|.
name|length
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"]"
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
name|ElementValue
index|[]
name|getElementValuesArray
parameter_list|()
block|{
return|return
name|elementValues
return|;
block|}
specifier|public
name|int
name|getElementValuesArraySize
parameter_list|()
block|{
return|return
name|elementValues
operator|.
name|length
return|;
block|}
block|}
end_class

end_unit

