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
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|AfterEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|VerifierTestCase
block|{
annotation|@
name|AfterEach
specifier|public
name|void
name|afterEach
parameter_list|()
block|{
name|VerifierFactory
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDefaultMethodValidation
parameter_list|()
block|{
specifier|final
name|String
name|className
init|=
name|Collection
operator|.
name|class
operator|.
name|getName
argument_list|()
decl_stmt|;
specifier|final
name|Verifier
name|verifier
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|className
argument_list|)
decl_stmt|;
name|VerificationResult
name|result
init|=
name|verifier
operator|.
name|doPass1
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_OK
argument_list|,
name|result
operator|.
name|getStatus
argument_list|()
argument_list|,
literal|"Pass 1 verification of "
operator|+
name|className
operator|+
literal|" failed: "
operator|+
name|result
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
name|verifier
operator|.
name|doPass2
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
name|VerificationResult
operator|.
name|VERIFIED_OK
argument_list|,
name|result
operator|.
name|getStatus
argument_list|()
argument_list|,
literal|"Pass 2 verification of "
operator|+
name|className
operator|+
literal|" failed: "
operator|+
name|result
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

