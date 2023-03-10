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
name|generic
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
name|ArrayTypeTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testGetBasicType
parameter_list|()
block|{
specifier|final
name|BasicType
name|type
init|=
name|Type
operator|.
name|BYTE
decl_stmt|;
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|type
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|type
argument_list|,
name|objectType
operator|.
name|getBasicType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetClassName
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[B"
argument_list|,
name|objectType
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|byte
index|[]
operator|.
expr|class
operator|.
name|getName
argument_list|()
argument_list|,
name|objectType
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetDimensions
parameter_list|()
block|{
specifier|final
name|int
name|dimensions
init|=
literal|1
decl_stmt|;
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
name|dimensions
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|dimensions
argument_list|,
name|objectType
operator|.
name|getDimensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetElementType
parameter_list|()
block|{
specifier|final
name|BasicType
name|type
init|=
name|Type
operator|.
name|BYTE
decl_stmt|;
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|type
argument_list|,
name|objectType
operator|.
name|getElementType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetSignatureDim1
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[B"
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|byte
index|[]
operator|.
expr|class
operator|.
name|getName
argument_list|()
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetSignatureDim2
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[[B"
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|byte
index|[]
index|[]
operator|.
expr|class
operator|.
name|getName
argument_list|()
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetSignatureDim4
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|4
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[[[[B"
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|byte
index|[]
index|[]
index|[]
index|[]
operator|.
expr|class
operator|.
name|getName
argument_list|()
argument_list|,
name|objectType
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetSize
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|objectType
operator|.
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetType
parameter_list|()
block|{
specifier|final
name|ArrayType
name|objectType
init|=
operator|new
name|ArrayType
argument_list|(
name|Type
operator|.
name|BYTE
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|13
argument_list|,
name|objectType
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

