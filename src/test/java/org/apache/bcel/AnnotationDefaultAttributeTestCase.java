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
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|AnnotationDefault
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
name|ElementValue
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
name|classfile
operator|.
name|Method
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
name|SimpleElementValue
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
name|AnnotationDefaultAttributeTestCase
extends|extends
name|AbstractTestCase
block|{
comment|/**      * For values in an annotation that have default values, we should be able to query the AnnotationDefault attribute      * against the method to discover the default value that was originally declared.      */
annotation|@
name|Test
specifier|public
name|void
name|testMethodAnnotations
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
literal|".data.SimpleAnnotation"
argument_list|)
decl_stmt|;
specifier|final
name|Method
name|m
init|=
name|getMethod
argument_list|(
name|clazz
argument_list|,
literal|"fruit"
argument_list|)
decl_stmt|;
specifier|final
name|AnnotationDefault
name|a
init|=
operator|(
name|AnnotationDefault
operator|)
name|findAttribute
argument_list|(
literal|"AnnotationDefault"
argument_list|,
name|m
operator|.
name|getAttributes
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|SimpleElementValue
name|val
init|=
operator|(
name|SimpleElementValue
operator|)
name|a
operator|.
name|getDefaultValue
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|ElementValue
operator|.
name|STRING
argument_list|,
name|val
operator|.
name|getElementValueType
argument_list|()
argument_list|,
literal|"Wrong element value type"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bananas"
argument_list|,
name|val
operator|.
name|getValueString
argument_list|()
argument_list|,
literal|"Wrong default"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

