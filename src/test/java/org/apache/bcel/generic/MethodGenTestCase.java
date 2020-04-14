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
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Repository
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
name|Test
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MailDateFormat
import|;
end_import

begin_class
specifier|public
class|class
name|MethodGenTestCase
block|{
annotation_defn|@interface
name|A
block|{     }
annotation_defn|@interface
name|B
block|{     }
specifier|public
specifier|static
class|class
name|Bar
block|{
specifier|public
class|class
name|Inner
block|{
specifier|public
name|Inner
parameter_list|(
annotation|@
name|A
specifier|final
name|Object
name|a
parameter_list|,
annotation|@
name|B
specifier|final
name|Object
name|b
parameter_list|)
block|{
block|}
block|}
block|}
specifier|public
specifier|static
class|class
name|Foo
block|{
specifier|public
name|void
name|bar
parameter_list|()
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|final
name|int
name|a
init|=
literal|1
decl_stmt|;
block|}
block|}
specifier|private
name|MethodGen
name|getMethod
parameter_list|(
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|cls
parameter_list|,
specifier|final
name|String
name|name
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|JavaClass
name|jc
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|cls
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPoolGen
name|cp
init|=
operator|new
name|ConstantPoolGen
argument_list|(
name|jc
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|Method
name|method
range|:
name|jc
operator|.
name|getMethods
argument_list|()
control|)
block|{
if|if
condition|(
name|method
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
operator|new
name|MethodGen
argument_list|(
name|method
argument_list|,
name|jc
operator|.
name|getClassName
argument_list|()
argument_list|,
name|cp
argument_list|)
return|;
block|}
block|}
name|Assert
operator|.
name|fail
argument_list|(
literal|"Method "
operator|+
name|name
operator|+
literal|" not found in class "
operator|+
name|cls
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAnnotationsAreUnpacked
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|JavaClass
name|jc
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|Bar
operator|.
name|Inner
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|final
name|ClassGen
name|cg
init|=
operator|new
name|ClassGen
argument_list|(
name|jc
argument_list|)
decl_stmt|;
specifier|final
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|cg
operator|.
name|getMethodAt
argument_list|(
literal|0
argument_list|)
argument_list|,
name|cg
operator|.
name|getClassName
argument_list|()
argument_list|,
name|cg
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|List
argument_list|<
name|AnnotationEntryGen
argument_list|>
name|firstParamAnnotations
init|=
name|mg
operator|.
name|getAnnotationsOnParameter
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Wrong number of annotations in the first parameter"
argument_list|,
literal|1
argument_list|,
name|firstParamAnnotations
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|List
argument_list|<
name|AnnotationEntryGen
argument_list|>
name|secondParamAnnotations
init|=
name|mg
operator|.
name|getAnnotationsOnParameter
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Wrong number of annotations in the second parameter"
argument_list|,
literal|1
argument_list|,
name|secondParamAnnotations
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemoveLocalVariable
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|MethodGen
name|mg
init|=
name|getMethod
argument_list|(
name|Foo
operator|.
name|class
argument_list|,
literal|"bar"
argument_list|)
decl_stmt|;
specifier|final
name|LocalVariableGen
name|lv
init|=
name|mg
operator|.
name|getLocalVariables
argument_list|()
index|[
literal|1
index|]
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"variable name"
argument_list|,
literal|"a"
argument_list|,
name|lv
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|start
init|=
name|lv
operator|.
name|getStart
argument_list|()
decl_stmt|;
specifier|final
name|InstructionHandle
name|end
init|=
name|lv
operator|.
name|getEnd
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"scope start"
argument_list|,
name|start
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"scope end"
argument_list|,
name|end
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"scope start not targeted by the local variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|start
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"scope end not targeted by the local variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|end
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
comment|// now let's remove the local variable
name|mg
operator|.
name|removeLocalVariable
argument_list|(
name|lv
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"scope start still targeted by the removed variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|start
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"scope end still targeted by the removed variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|end
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
literal|"scope start"
argument_list|,
name|lv
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
literal|"scope end"
argument_list|,
name|lv
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRemoveLocalVariables
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|MethodGen
name|mg
init|=
name|getMethod
argument_list|(
name|Foo
operator|.
name|class
argument_list|,
literal|"bar"
argument_list|)
decl_stmt|;
specifier|final
name|LocalVariableGen
name|lv
init|=
name|mg
operator|.
name|getLocalVariables
argument_list|()
index|[
literal|1
index|]
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"variable name"
argument_list|,
literal|"a"
argument_list|,
name|lv
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|start
init|=
name|lv
operator|.
name|getStart
argument_list|()
decl_stmt|;
specifier|final
name|InstructionHandle
name|end
init|=
name|lv
operator|.
name|getEnd
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"scope start"
argument_list|,
name|start
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"scope end"
argument_list|,
name|end
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"scope start not targeted by the local variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|start
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"scope end not targeted by the local variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|end
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
comment|// now let's remove the local variables
name|mg
operator|.
name|removeLocalVariables
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"scope start still targeted by the removed variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|start
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"scope end still targeted by the removed variable"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|end
operator|.
name|getTargeters
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|lv
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
literal|"scope start"
argument_list|,
name|lv
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
literal|"scope end"
argument_list|,
name|lv
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalStateException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testInvalidNullMethodBody_MailDateFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|testInvalidNullMethodBody
argument_list|(
literal|"javax.mail.internet.MailDateFormat"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testInvalidNullMethodBody_EmptyStaticInit
parameter_list|()
throws|throws
name|Exception
block|{
name|testInvalidNullMethodBody
argument_list|(
literal|"org.apache.bcel.generic.EmptyStaticInit"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|testInvalidNullMethodBody
parameter_list|(
specifier|final
name|String
name|className
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|JavaClass
name|jc
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|className
argument_list|)
decl_stmt|;
name|ClassGen
name|classGen
init|=
operator|new
name|ClassGen
argument_list|(
name|jc
argument_list|)
decl_stmt|;
for|for
control|(
name|Method
name|method
range|:
name|jc
operator|.
name|getMethods
argument_list|()
control|)
block|{
operator|new
name|MethodGen
argument_list|(
name|method
argument_list|,
name|jc
operator|.
name|getClassName
argument_list|()
argument_list|,
name|classGen
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

