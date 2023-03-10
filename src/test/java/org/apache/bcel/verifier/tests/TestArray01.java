begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|tests
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_class
specifier|public
class|class
name|TestArray01
block|{
specifier|public
specifier|static
name|Object
name|foo
parameter_list|(
specifier|final
name|String
name|s
parameter_list|)
block|{
return|return
name|s
return|;
block|}
specifier|public
specifier|static
name|Object
name|test1
parameter_list|()
block|{
specifier|final
name|String
index|[]
name|a
init|=
operator|new
name|String
index|[
literal|4
index|]
decl_stmt|;
name|a
index|[
literal|0
index|]
operator|=
literal|""
expr_stmt|;
name|a
operator|.
name|equals
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|test2
argument_list|(
name|a
argument_list|)
expr_stmt|;
name|test3
argument_list|(
name|a
argument_list|)
expr_stmt|;
name|test4
argument_list|(
name|a
argument_list|)
expr_stmt|;
return|return
name|a
return|;
block|}
specifier|public
specifier|static
name|void
name|test2
parameter_list|(
specifier|final
name|Object
name|o
parameter_list|)
block|{
block|}
specifier|public
specifier|static
name|void
name|test3
parameter_list|(
specifier|final
name|Serializable
name|o
parameter_list|)
block|{
block|}
specifier|public
specifier|static
name|void
name|test4
parameter_list|(
specifier|final
name|Cloneable
name|o
parameter_list|)
block|{
block|}
specifier|public
specifier|static
name|Serializable
name|test5
parameter_list|()
block|{
return|return
operator|new
name|Object
index|[
literal|1
index|]
return|;
block|}
specifier|public
specifier|static
name|Cloneable
name|test6
parameter_list|()
block|{
return|return
operator|new
name|Object
index|[
literal|1
index|]
return|;
block|}
block|}
end_class

end_unit

