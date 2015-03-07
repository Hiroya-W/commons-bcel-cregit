begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
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
package|;
end_package

begin_class
specifier|public
class|class
name|VerifierArrayAccessTestCase
extends|extends
name|AbstractVerifierTestCase
block|{
specifier|public
name|void
name|testInvalidArrayAccess
parameter_list|()
block|{
name|assertVerifyRejected
argument_list|(
literal|"TestArrayAccess03"
argument_list|,
literal|"Verification of an arraystore instruction on an object must fail."
argument_list|)
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestArrayAccess04"
argument_list|,
literal|"Verification of an arraystore instruction of an int on an array of references must fail."
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testValidArrayAccess
parameter_list|()
block|{
name|assertVerifyOK
argument_list|(
literal|"TestArrayAccess01"
argument_list|,
literal|"Verification of an arraystore instruction on an array that is not compatible with the stored element must pass."
argument_list|)
expr_stmt|;
name|assertVerifyOK
argument_list|(
literal|"TestArrayAccess02"
argument_list|,
literal|"Verification of an arraystore instruction on an array that is not compatible with the stored element must pass."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

