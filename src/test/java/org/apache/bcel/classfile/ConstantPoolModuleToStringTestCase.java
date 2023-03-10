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
name|assertTrue
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
name|fail
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
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Files
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
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|StringUtils
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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
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
name|params
operator|.
name|provider
operator|.
name|ValueSource
import|;
end_import

begin_comment
comment|/**  * Tests {@code module-info.class} files.  */
end_comment

begin_class
specifier|public
class|class
name|ConstantPoolModuleToStringTestCase
block|{
specifier|static
class|class
name|ToStringVisitor
extends|extends
name|EmptyVisitor
block|{
specifier|private
specifier|final
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|ConstantPool
name|pool
decl_stmt|;
specifier|private
name|int
name|count
decl_stmt|;
specifier|public
name|ToStringVisitor
parameter_list|(
specifier|final
name|ConstantPool
name|pool
parameter_list|)
block|{
name|this
operator|.
name|pool
operator|=
name|pool
expr_stmt|;
block|}
specifier|private
name|void
name|append
parameter_list|(
specifier|final
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|stringBuilder
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"ToStringVisitor [count="
operator|+
name|count
operator|+
literal|", stringBuilder="
operator|+
name|stringBuilder
operator|+
literal|", pool="
operator|+
name|pool
operator|+
literal|"]"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitAnnotation
parameter_list|(
specifier|final
name|Annotations
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitAnnotation
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitAnnotationDefault
parameter_list|(
specifier|final
name|AnnotationDefault
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitAnnotationDefault
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitAnnotationEntry
parameter_list|(
specifier|final
name|AnnotationEntry
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitAnnotationEntry
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitBootstrapMethods
parameter_list|(
specifier|final
name|BootstrapMethods
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitBootstrapMethods
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitCode
parameter_list|(
specifier|final
name|Code
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitCode
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
operator|.
name|toString
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitCodeException
parameter_list|(
specifier|final
name|CodeException
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitCodeException
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
operator|.
name|toString
argument_list|(
name|pool
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantClass
parameter_list|(
specifier|final
name|ConstantClass
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantClass
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantDouble
parameter_list|(
specifier|final
name|ConstantDouble
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantDouble
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantDynamic
parameter_list|(
specifier|final
name|ConstantDynamic
name|constantDynamic
parameter_list|)
block|{
name|super
operator|.
name|visitConstantDynamic
argument_list|(
name|constantDynamic
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantDynamic
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantFieldref
parameter_list|(
specifier|final
name|ConstantFieldref
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantFieldref
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantFloat
parameter_list|(
specifier|final
name|ConstantFloat
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantFloat
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantInteger
parameter_list|(
specifier|final
name|ConstantInteger
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantInteger
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantInterfaceMethodref
parameter_list|(
specifier|final
name|ConstantInterfaceMethodref
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantInterfaceMethodref
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantInvokeDynamic
parameter_list|(
specifier|final
name|ConstantInvokeDynamic
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantInvokeDynamic
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantLong
parameter_list|(
specifier|final
name|ConstantLong
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantLong
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantMethodHandle
parameter_list|(
specifier|final
name|ConstantMethodHandle
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantMethodHandle
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantMethodref
parameter_list|(
specifier|final
name|ConstantMethodref
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantMethodref
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantMethodType
parameter_list|(
specifier|final
name|ConstantMethodType
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantMethodType
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantModule
parameter_list|(
specifier|final
name|ConstantModule
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitConstantModule
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantNameAndType
parameter_list|(
specifier|final
name|ConstantNameAndType
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantNameAndType
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantPackage
parameter_list|(
specifier|final
name|ConstantPackage
name|constantPackage
parameter_list|)
block|{
name|super
operator|.
name|visitConstantPackage
argument_list|(
name|constantPackage
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantPackage
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantPool
parameter_list|(
specifier|final
name|ConstantPool
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantPool
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantString
parameter_list|(
specifier|final
name|ConstantString
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantString
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantUtf8
parameter_list|(
specifier|final
name|ConstantUtf8
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantUtf8
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantValue
parameter_list|(
specifier|final
name|ConstantValue
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitConstantValue
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitDeprecated
parameter_list|(
specifier|final
name|Deprecated
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitDeprecated
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitEnclosingMethod
parameter_list|(
specifier|final
name|EnclosingMethod
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitEnclosingMethod
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitExceptionTable
parameter_list|(
specifier|final
name|ExceptionTable
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitExceptionTable
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitField
parameter_list|(
specifier|final
name|Field
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitField
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitInnerClass
parameter_list|(
specifier|final
name|InnerClass
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitInnerClass
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitInnerClasses
parameter_list|(
specifier|final
name|InnerClasses
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitInnerClasses
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitJavaClass
parameter_list|(
specifier|final
name|JavaClass
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitJavaClass
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLineNumber
parameter_list|(
specifier|final
name|LineNumber
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitLineNumber
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLineNumberTable
parameter_list|(
specifier|final
name|LineNumberTable
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitLineNumberTable
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLocalVariable
parameter_list|(
specifier|final
name|LocalVariable
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitLocalVariable
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLocalVariableTable
parameter_list|(
specifier|final
name|LocalVariableTable
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitLocalVariableTable
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLocalVariableTypeTable
parameter_list|(
specifier|final
name|LocalVariableTypeTable
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitLocalVariableTypeTable
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitMethod
parameter_list|(
specifier|final
name|Method
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitMethod
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitMethodParameter
parameter_list|(
specifier|final
name|MethodParameter
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitMethodParameter
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitMethodParameters
parameter_list|(
specifier|final
name|MethodParameters
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitMethodParameters
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModule
parameter_list|(
specifier|final
name|Module
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModule
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|()
decl_stmt|;
specifier|final
name|Matcher
name|matcher
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"  (\\w+)([:(])"
argument_list|)
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
switch|switch
condition|(
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
condition|)
block|{
case|case
literal|":"
case|:
name|assertTrue
argument_list|(
name|StringUtils
operator|.
name|containsAny
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|,
literal|"name"
argument_list|,
literal|"flags"
argument_list|,
literal|"version"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|"("
case|:
name|assertTrue
argument_list|(
name|StringUtils
operator|.
name|containsAny
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|,
literal|"requires"
argument_list|,
literal|"exports"
argument_list|,
literal|"opens"
argument_list|,
literal|"uses"
argument_list|,
literal|"provides"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModuleExports
parameter_list|(
specifier|final
name|ModuleExports
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModuleExports
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
decl_stmt|;
specifier|final
name|String
index|[]
name|tokens
init|=
name|s
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|tokens
operator|.
name|length
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"0000"
argument_list|,
name|tokens
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
specifier|final
name|Matcher
name|matcher
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"to\\((\\d+)\\):"
argument_list|)
operator|.
name|matcher
argument_list|(
name|tokens
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcher
operator|.
name|find
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|,
name|StringUtils
operator|.
name|countMatches
argument_list|(
name|s
argument_list|,
literal|'\n'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModuleMainClass
parameter_list|(
specifier|final
name|ModuleMainClass
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitModuleMainClass
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModuleOpens
parameter_list|(
specifier|final
name|ModuleOpens
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModuleOpens
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
decl_stmt|;
specifier|final
name|String
index|[]
name|tokens
init|=
name|s
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|tokens
operator|.
name|length
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"0000"
argument_list|,
name|tokens
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
specifier|final
name|Matcher
name|matcher
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"to\\((\\d+)\\):"
argument_list|)
operator|.
name|matcher
argument_list|(
name|tokens
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcher
operator|.
name|find
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|,
name|StringUtils
operator|.
name|countMatches
argument_list|(
name|s
argument_list|,
literal|'\n'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModulePackages
parameter_list|(
specifier|final
name|ModulePackages
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModulePackages
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|constantModule
operator|.
name|getNumberOfPackages
argument_list|()
argument_list|,
name|StringUtils
operator|.
name|countMatches
argument_list|(
name|s
argument_list|,
literal|'\n'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModuleProvides
parameter_list|(
specifier|final
name|ModuleProvides
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModuleProvides
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
decl_stmt|;
specifier|final
name|String
index|[]
name|tokens
init|=
name|s
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|tokens
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|tokens
operator|.
name|length
argument_list|)
expr_stmt|;
specifier|final
name|Matcher
name|matcher
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"with\\((\\d+)\\):"
argument_list|)
operator|.
name|matcher
argument_list|(
name|tokens
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcher
operator|.
name|find
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|,
name|StringUtils
operator|.
name|countMatches
argument_list|(
name|s
argument_list|,
literal|'\n'
argument_list|)
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitModuleRequires
parameter_list|(
specifier|final
name|ModuleRequires
name|constantModule
parameter_list|)
block|{
name|super
operator|.
name|visitModuleRequires
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|constantModule
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|String
name|s
init|=
name|constantModule
operator|.
name|toString
argument_list|(
name|pool
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|StringUtils
operator|.
name|startsWithAny
argument_list|(
name|s
argument_list|,
literal|"jdk."
argument_list|,
literal|"java."
argument_list|,
literal|"org.junit"
argument_list|,
literal|"org.apiguardian.api"
argument_list|,
literal|"org.opentest4j"
argument_list|)
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitNestHost
parameter_list|(
specifier|final
name|NestHost
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitNestHost
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitNestMembers
parameter_list|(
specifier|final
name|NestMembers
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitNestMembers
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitParameterAnnotation
parameter_list|(
specifier|final
name|ParameterAnnotations
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitParameterAnnotation
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitParameterAnnotationEntry
parameter_list|(
specifier|final
name|ParameterAnnotationEntry
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitParameterAnnotationEntry
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitSignature
parameter_list|(
specifier|final
name|Signature
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitSignature
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitSourceFile
parameter_list|(
specifier|final
name|SourceFile
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitSourceFile
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitStackMap
parameter_list|(
specifier|final
name|StackMap
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitStackMap
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitStackMapEntry
parameter_list|(
specifier|final
name|StackMapEntry
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitStackMapEntry
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitSynthetic
parameter_list|(
specifier|final
name|Synthetic
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitSynthetic
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitUnknown
parameter_list|(
specifier|final
name|Unknown
name|obj
parameter_list|)
block|{
name|super
operator|.
name|visitUnknown
argument_list|(
name|obj
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|test
parameter_list|(
specifier|final
name|InputStream
name|inputStream
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|ClassParser
name|classParser
init|=
operator|new
name|ClassParser
argument_list|(
name|inputStream
argument_list|,
literal|"module-info.class"
argument_list|)
decl_stmt|;
specifier|final
name|JavaClass
name|javaClass
init|=
name|classParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|testJavaClass
argument_list|(
name|javaClass
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|testJavaClass
parameter_list|(
specifier|final
name|JavaClass
name|javaClass
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|constantPool
init|=
name|javaClass
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ToStringVisitor
name|visitor
init|=
operator|new
name|ToStringVisitor
argument_list|(
name|constantPool
argument_list|)
decl_stmt|;
specifier|final
name|DescendingVisitor
name|descendingVisitor
init|=
operator|new
name|DescendingVisitor
argument_list|(
name|javaClass
argument_list|,
name|visitor
argument_list|)
decl_stmt|;
try|try
block|{
name|javaClass
operator|.
name|accept
argument_list|(
name|descendingVisitor
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|visitor
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
decl||
name|Error
name|e
parameter_list|)
block|{
name|fail
argument_list|(
name|visitor
operator|.
name|toString
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|test
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|Enumeration
argument_list|<
name|URL
argument_list|>
name|moduleURLs
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResources
argument_list|(
literal|"module-info.class"
argument_list|)
decl_stmt|;
while|while
condition|(
name|moduleURLs
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
specifier|final
name|URL
name|url
init|=
name|moduleURLs
operator|.
name|nextElement
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|inputStream
init|=
name|url
operator|.
name|openStream
argument_list|()
init|)
block|{
name|test
argument_list|(
name|inputStream
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|ValueSource
argument_list|(
name|strings
operator|=
block|{
comment|// @formatter:off
literal|"src/test/resources/jpms/java11/commons-io/module-info.class"
block|,
literal|"src/test/resources/jpms/java17/commons-io/module-info.class"
block|,
literal|"src/test/resources/jpms/java18/commons-io/module-info.class"
block|,
literal|"src/test/resources/jpms/java19-ea/commons-io/module-info.class"
block|}
argument_list|)
comment|// @formatter:on
specifier|public
name|void
name|test
parameter_list|(
specifier|final
name|String
name|first
parameter_list|)
throws|throws
name|Exception
block|{
try|try
init|(
specifier|final
name|InputStream
name|inputStream
init|=
name|Files
operator|.
name|newInputStream
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|first
argument_list|)
argument_list|)
init|)
block|{
name|test
argument_list|(
name|inputStream
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|ValueSource
argument_list|(
name|strings
operator|=
block|{
comment|// @formatter:off
literal|"java.lang.CharSequence$1CharIterator"
block|,
comment|// contains attribute EnclosingMethod
literal|"org.apache.commons.lang3.function.TriFunction"
block|,
comment|// contains attributes BootstrapMethods, InnerClasses, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, RuntimeVisibleAnnotations, Signature, SourceFile
literal|"org.apache.commons.lang3.math.NumberUtils"
block|,
comment|// contains attribute ConstantFloat, ConstantDouble
literal|"org.apache.bcel.Const"
block|,
comment|// contains attributes MethodParameters
literal|"java.io.StringBufferInputStream"
block|,
comment|// contains attributes Deprecated, StackMap
literal|"java.nio.file.Files"
block|,
comment|// contains attributes ConstantValue, ExceptionTable, NestMembers
literal|"org.junit.jupiter.api.AssertionsKt"
block|,
comment|// contains attribute ParameterAnnotation
literal|"javax.annotation.ManagedBean"
block|,
comment|// contains attribute AnnotationDefault
literal|"javax.management.remote.rmi.RMIConnectionImpl_Stub"
block|}
argument_list|)
comment|// contains attribute Synthetic
comment|// @formatter:on
specifier|public
name|void
name|testClass
parameter_list|(
specifier|final
name|String
name|className
parameter_list|)
throws|throws
name|Exception
block|{
name|testJavaClass
argument_list|(
name|SyntheticRepository
operator|.
name|getInstance
argument_list|()
operator|.
name|loadClass
argument_list|(
name|className
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

