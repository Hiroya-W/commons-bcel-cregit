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
name|classfile
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
name|assertArrayEquals
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

begin_comment
comment|/**  * Tests {@link StackMap}.  */
end_comment

begin_class
specifier|public
class|class
name|StackMapTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testSetStackMap
parameter_list|()
block|{
specifier|final
name|StackMap
name|stackMap
init|=
operator|new
name|StackMap
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|StackMapEntry
operator|.
name|EMPTY_ARRAY
argument_list|,
operator|new
name|ConstantPool
argument_list|(
operator|new
name|Constant
index|[]
block|{
operator|new
name|ConstantLong
argument_list|(
literal|0
argument_list|)
block|}
argument_list|)
argument_list|)
decl_stmt|;
comment|// No NPE
name|stackMap
operator|.
name|setStackMap
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|assertArrayEquals
argument_list|(
name|StackMapEntry
operator|.
name|EMPTY_ARRAY
argument_list|,
name|stackMap
operator|.
name|getStackMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

