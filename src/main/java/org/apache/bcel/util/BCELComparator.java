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

begin_comment
comment|/**  * Used for BCEL comparison strategy  *  * @version $Id$  * @since 5.2  */
end_comment

begin_interface
specifier|public
interface|interface
name|BCELComparator
block|{
comment|/**      * Compare two objects and return what THIS.equals(THAT) should return      *      * @param THIS      * @param THAT      * @return true if and only if THIS equals THAT      */
name|boolean
name|equals
parameter_list|(
name|Object
name|THIS
parameter_list|,
name|Object
name|THAT
parameter_list|)
function_decl|;
comment|/**      * Return hashcode for THIS.hashCode()      *      * @param THIS      * @return hashcode for THIS.hashCode()      */
name|int
name|hashCode
parameter_list|(
name|Object
name|THIS
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

