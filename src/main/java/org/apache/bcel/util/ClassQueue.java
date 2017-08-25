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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|JavaClass
import|;
end_import

begin_comment
comment|/**  * Utility class implementing a (typesafe) queue of JavaClass  * objects.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|ClassQueue
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access      */
annotation|@
name|Deprecated
specifier|protected
name|LinkedList
argument_list|<
name|JavaClass
argument_list|>
name|vec
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// TODO not used externally
specifier|public
name|void
name|enqueue
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|)
block|{
name|vec
operator|.
name|addLast
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
specifier|public
name|JavaClass
name|dequeue
parameter_list|()
block|{
return|return
name|vec
operator|.
name|removeFirst
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|empty
parameter_list|()
block|{
return|return
name|vec
operator|.
name|isEmpty
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
name|vec
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

