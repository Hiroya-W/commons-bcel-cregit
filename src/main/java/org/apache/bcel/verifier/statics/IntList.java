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
name|verifier
operator|.
name|statics
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
name|List
import|;
end_import

begin_comment
comment|/**  * A small utility class representing a set of basic int values.  */
end_comment

begin_class
specifier|public
class|class
name|IntList
block|{
comment|/** The int are stored as Integer objects here. */
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|list
decl_stmt|;
comment|/** This constructor creates an empty list. */
name|IntList
parameter_list|()
block|{
name|list
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
comment|/** Adds an element to the list. */
name|void
name|add
parameter_list|(
specifier|final
name|int
name|i
parameter_list|)
block|{
name|list
operator|.
name|add
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** Checks if the specified int is already in the list. */
name|boolean
name|contains
parameter_list|(
specifier|final
name|int
name|i
parameter_list|)
block|{
return|return
name|list
operator|.
name|contains
argument_list|(
name|i
argument_list|)
return|;
block|}
block|}
end_class

end_unit

