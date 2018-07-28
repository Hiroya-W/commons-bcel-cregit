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
operator|.
name|util
package|;
end_package

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
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
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
name|java
operator|.
name|util
operator|.
name|List
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
name|generic
operator|.
name|JdkGenericDumpTestCase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|JavaVersion
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|SystemUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assume
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
import|;
end_import

begin_comment
comment|/**  * Tests {@link ModularRuntimeImage}.  */
end_comment

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ModularRuntimeImageTestCase
block|{
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{0}"
argument_list|)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|data
parameter_list|()
block|{
return|return
name|JdkGenericDumpTestCase
operator|.
name|data
argument_list|()
return|;
block|}
specifier|private
specifier|final
name|String
name|javaHome
decl_stmt|;
specifier|private
specifier|final
name|ModularRuntimeImage
name|modularRuntimeImage
decl_stmt|;
specifier|public
name|ModularRuntimeImageTestCase
parameter_list|(
specifier|final
name|String
name|javaHome
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|javaHome
operator|=
name|javaHome
expr_stmt|;
name|Assume
operator|.
name|assumeTrue
argument_list|(
name|SystemUtils
operator|.
name|isJavaVersionAtLeast
argument_list|(
name|JavaVersion
operator|.
name|JAVA_9
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|modularRuntimeImage
operator|=
operator|new
name|ModularRuntimeImage
argument_list|(
name|javaHome
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testListJreModules
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|listEntries
init|=
name|modularRuntimeImage
operator|.
name|list
argument_list|(
name|ModularRuntimeImage
operator|.
name|MODULES_PATH
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|listEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|listEntries
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"/java.base"
argument_list|)
operator|>
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testListJreModule
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|listEntries
init|=
name|modularRuntimeImage
operator|.
name|list
argument_list|(
name|ModularRuntimeImage
operator|.
name|MODULES_PATH
operator|+
literal|"/java.base"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|listEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|listEntries
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"/java.base"
argument_list|)
operator|>
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testListJreModulePackageDir
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|listEntries
init|=
name|modularRuntimeImage
operator|.
name|list
argument_list|(
name|ModularRuntimeImage
operator|.
name|MODULES_PATH
operator|+
literal|"/java.base/java/lang"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|listEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|listEntries
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"/java.base/java/lang/String.class"
argument_list|)
operator|>
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testListJrePackages
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|listEntries
init|=
name|modularRuntimeImage
operator|.
name|list
argument_list|(
name|ModularRuntimeImage
operator|.
name|PACKAGES_PATH
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|listEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|listEntries
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"java.lang"
argument_list|)
operator|>
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

