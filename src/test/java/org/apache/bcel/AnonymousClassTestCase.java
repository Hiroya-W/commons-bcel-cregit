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
package|;
end_package

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

begin_class
specifier|public
class|class
name|AnonymousClassTestCase
extends|extends
name|AbstractTestCase
block|{
specifier|public
name|void
name|testRegularClassIsNotAnonymous
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AnonymousClassTest"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
literal|"regular outer classes are not anonymous"
argument_list|,
name|clazz
operator|.
name|isAnonymous
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
literal|"regular outer classes are not nested"
argument_list|,
name|clazz
operator|.
name|isNested
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testNamedInnerClassIsNotAnonymous
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AnonymousClassTest$X"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
literal|"regular inner classes are not anonymous"
argument_list|,
name|clazz
operator|.
name|isAnonymous
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"regular inner classes are nested"
argument_list|,
name|clazz
operator|.
name|isNested
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testStaticInnerClassIsNotAnonymous
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AnonymousClassTest$Y"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
literal|"regular static inner classes are not anonymous"
argument_list|,
name|clazz
operator|.
name|isAnonymous
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"regular static inner classes are nested"
argument_list|,
name|clazz
operator|.
name|isNested
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testAnonymousInnerClassIsAnonymous
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.AnonymousClassTest$1"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"anonymous inner classes are anonymous"
argument_list|,
name|clazz
operator|.
name|isAnonymous
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
literal|"anonymous inner classes are anonymous"
argument_list|,
name|clazz
operator|.
name|isNested
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
