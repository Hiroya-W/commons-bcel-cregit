begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
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
name|assertNotNull
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Attribute
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
name|ConstantPool
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
name|EnclosingMethod
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
operator|.
name|SyntheticRepository
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
name|EnclosingMethodAttributeTestCase
extends|extends
name|AbstractTestCase
block|{
comment|/**      * Check that we can save and load the attribute correctly.      */
annotation|@
name|Test
specifier|public
name|void
name|testAttributeSerializtion
parameter_list|()
throws|throws
name|ClassNotFoundException
throws|,
name|IOException
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|getTestJavaClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AttributeTestClassEM02$1"
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPool
name|pool
init|=
name|clazz
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|Attribute
index|[]
name|encMethodAttrs
init|=
name|findAttribute
argument_list|(
literal|"EnclosingMethod"
argument_list|,
name|clazz
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|encMethodAttrs
operator|.
name|length
argument_list|,
literal|"Wrong number of EnclosingMethod attributes"
argument_list|)
expr_stmt|;
comment|// Write it out
specifier|final
name|File
name|tfile
init|=
name|createTestdataFile
argument_list|(
literal|"AttributeTestClassEM02$1.class"
argument_list|)
decl_stmt|;
name|clazz
operator|.
name|dump
argument_list|(
name|tfile
argument_list|)
expr_stmt|;
comment|// Read in the new version and check it is OK
specifier|final
name|SyntheticRepository
name|repos2
init|=
name|createRepos
argument_list|(
literal|"."
argument_list|)
decl_stmt|;
specifier|final
name|JavaClass
name|clazz2
init|=
name|repos2
operator|.
name|loadClass
argument_list|(
literal|"AttributeTestClassEM02$1"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|clazz2
argument_list|)
expr_stmt|;
comment|// Use the variable to avoid a warning
specifier|final
name|EnclosingMethod
name|em
init|=
operator|(
name|EnclosingMethod
operator|)
name|encMethodAttrs
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|String
name|enclosingClassName
init|=
name|em
operator|.
name|getEnclosingClass
argument_list|()
operator|.
name|getBytes
argument_list|(
name|pool
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|em
operator|.
name|getEnclosingMethodIndex
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|"The class is not within a method, so method_index should be null"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|PACKAGE_BASE_SIG
operator|+
literal|"/data/AttributeTestClassEM02"
argument_list|,
name|enclosingClassName
argument_list|,
literal|"Wrong class name"
argument_list|)
expr_stmt|;
name|tfile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
block|}
comment|/**      * Verify for an inner class declared at the type level that the EnclosingMethod attribute is set correctly (i.e. to a      * null value)      */
annotation|@
name|Test
specifier|public
name|void
name|testCheckClassLevelNamedInnerClass
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|getTestJavaClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AttributeTestClassEM02$1"
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPool
name|pool
init|=
name|clazz
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|Attribute
index|[]
name|encMethodAttrs
init|=
name|findAttribute
argument_list|(
literal|"EnclosingMethod"
argument_list|,
name|clazz
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|encMethodAttrs
operator|.
name|length
argument_list|,
literal|"Expected 1 EnclosingMethod attribute but found "
operator|+
name|encMethodAttrs
operator|.
name|length
argument_list|)
expr_stmt|;
specifier|final
name|EnclosingMethod
name|em
init|=
operator|(
name|EnclosingMethod
operator|)
name|encMethodAttrs
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|String
name|enclosingClassName
init|=
name|em
operator|.
name|getEnclosingClass
argument_list|()
operator|.
name|getBytes
argument_list|(
name|pool
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|em
operator|.
name|getEnclosingMethodIndex
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|"The class is not within a method, so method_index should be null"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|PACKAGE_BASE_SIG
operator|+
literal|"/data/AttributeTestClassEM02"
argument_list|,
name|enclosingClassName
argument_list|,
literal|"Wrong class name"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verify for an inner class declared inside the 'main' method that the enclosing method attribute is set correctly.      */
annotation|@
name|Test
specifier|public
name|void
name|testCheckMethodLevelNamedInnerClass
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|getTestJavaClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AttributeTestClassEM01$1S"
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPool
name|pool
init|=
name|clazz
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|Attribute
index|[]
name|encMethodAttrs
init|=
name|findAttribute
argument_list|(
literal|"EnclosingMethod"
argument_list|,
name|clazz
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|encMethodAttrs
operator|.
name|length
argument_list|,
literal|"Wrong number of EnclosingMethod attributes"
argument_list|)
expr_stmt|;
specifier|final
name|EnclosingMethod
name|em
init|=
operator|(
name|EnclosingMethod
operator|)
name|encMethodAttrs
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|String
name|enclosingClassName
init|=
name|em
operator|.
name|getEnclosingClass
argument_list|()
operator|.
name|getBytes
argument_list|(
name|pool
argument_list|)
decl_stmt|;
specifier|final
name|String
name|enclosingMethodName
init|=
name|em
operator|.
name|getEnclosingMethod
argument_list|()
operator|.
name|getName
argument_list|(
name|pool
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|PACKAGE_BASE_SIG
operator|+
literal|"/data/AttributeTestClassEM01"
argument_list|,
name|enclosingClassName
argument_list|,
literal|"Wrong class name"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|enclosingMethodName
argument_list|,
literal|"main"
argument_list|,
literal|"Wrong method name"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

