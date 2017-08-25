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
name|CounterVisitorTestCase
extends|extends
name|AbstractCounterVisitorTestCase
block|{
annotation|@
name|Override
specifier|protected
name|JavaClass
name|getTestClass
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
return|return
name|getTestClass
argument_list|(
name|PACKAGE_BASE_NAME
operator|+
literal|".data.MarkedType"
argument_list|)
return|;
block|}
specifier|public
name|void
name|testAnnotationsCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"annotationCount"
argument_list|,
literal|2
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|annotationCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testAnnotationDefaultCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"annotationDefaultCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|annotationDefaultCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testAnnotationEntryCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"annotationEntryCount"
argument_list|,
literal|2
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|annotationEntryCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCodeCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"codeCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|codeCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCodeExceptionCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"codeExceptionCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|codeExceptionCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantClassCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantClassCount"
argument_list|,
literal|2
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantClassCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantDoubleCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantDoubleCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantDoubleCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantFieldrefCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantFieldrefCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantFieldrefCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantFloatCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantFloatCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantFloatCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantIntegerCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantIntegerCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantIntegerCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantInterfaceMethodrefCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantInterfaceMethodrefCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantInterfaceMethodrefCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantLongCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantLongCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantLongCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantMethodrefCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantMethodrefCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantMethodrefCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantNameAndTypeCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantNameAndTypeCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantNameAndTypeCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantPoolCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantPoolCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantPoolCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantStringCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantStringCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantStringCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testConstantValueCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"constantValueCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|constantValueCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testDeprecatedCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"deprecatedCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|deprecatedCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testEnclosingMethodCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"enclosingMethodCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|enclosingMethodCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testExceptionTableCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"exceptionTableCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|exceptionTableCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testFieldCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"fieldCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|fieldCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testInnerClassCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"innerClassCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|innerClassCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testInnerClassesCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"innerClassesCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|innerClassesCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testJavaClassCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"javaClassCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|javaClassCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testLineNumberCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"lineNumberCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|lineNumberCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testLineNumberTableCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"lineNumberTableCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|lineNumberTableCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testLocalVariableCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"localVariableCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|localVariableCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testLocalVariableTableCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"localVariableTableCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|localVariableTableCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testLocalVariableTypeTableCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"localVariableTypeTableCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|localVariableTypeTableCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testMethodCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"methodCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|methodCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testParameterAnnotationCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"parameterAnnotationCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|parameterAnnotationCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testSignatureCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"signatureAnnotationCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|signatureAnnotationCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testSourceFileCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"sourceFileCount"
argument_list|,
literal|1
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|sourceFileCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testStackMapCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"stackMapCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|stackMapCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testStackMapEntryCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"stackMapEntryCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|stackMapEntryCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testSyntheticCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"syntheticCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|syntheticCount
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testUnknownCount
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"unknownCount"
argument_list|,
literal|0
argument_list|,
name|getVisitor
argument_list|()
operator|.
name|unknownCount
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

