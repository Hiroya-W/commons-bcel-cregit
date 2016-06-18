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
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
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
name|generic
operator|.
name|ClassElementValueGen
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
name|ClassGen
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
name|ConstantPoolGen
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
name|ElementValueGen
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
name|EnumElementValueGen
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
name|ObjectType
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
name|SimpleElementValueGen
import|;
end_import

begin_class
specifier|public
class|class
name|ElementValueGenTestCase
extends|extends
name|AbstractTestCase
block|{
specifier|private
name|ClassGen
name|createClassGen
parameter_list|(
specifier|final
name|String
name|classname
parameter_list|)
block|{
return|return
operator|new
name|ClassGen
argument_list|(
name|classname
argument_list|,
literal|"java.lang.Object"
argument_list|,
literal|"<generated>"
argument_list|,
name|Const
operator|.
name|ACC_PUBLIC
operator||
name|Const
operator|.
name|ACC_SUPER
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Create primitive element values      */
specifier|public
name|void
name|testCreateIntegerElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_INT
argument_list|,
name|cp
argument_list|,
literal|555
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|cp
operator|.
name|lookupInteger
argument_list|(
literal|555
argument_list|)
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|cp
operator|.
name|lookupInteger
argument_list|(
literal|555
argument_list|)
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateFloatElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_FLOAT
argument_list|,
name|cp
argument_list|,
literal|111.222f
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|cp
operator|.
name|lookupFloat
argument_list|(
literal|111.222f
argument_list|)
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|cp
operator|.
name|lookupFloat
argument_list|(
literal|111.222f
argument_list|)
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateDoubleElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_DOUBLE
argument_list|,
name|cp
argument_list|,
literal|333.44
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupDouble
argument_list|(
literal|333.44
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateLongElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_LONG
argument_list|,
name|cp
argument_list|,
literal|3334455L
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupLong
argument_list|(
literal|3334455L
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateCharElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_CHAR
argument_list|,
name|cp
argument_list|,
literal|'t'
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupInteger
argument_list|(
literal|'t'
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateByteElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_CHAR
argument_list|,
name|cp
argument_list|,
operator|(
name|byte
operator|)
literal|'z'
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupInteger
argument_list|(
operator|(
name|byte
operator|)
literal|'z'
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateBooleanElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_BOOLEAN
argument_list|,
name|cp
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupInteger
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|// 1 == true
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|testCreateShortElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|PRIMITIVE_SHORT
argument_list|,
name|cp
argument_list|,
operator|(
name|short
operator|)
literal|42
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|int
name|idx
init|=
name|cp
operator|.
name|lookupInteger
argument_list|(
literal|42
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|idx
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|idx
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
comment|// //
comment|// Create string element values
specifier|public
name|void
name|testCreateStringElementValue
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Create HelloWorld
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|SimpleElementValueGen
name|evg
init|=
operator|new
name|SimpleElementValueGen
argument_list|(
name|ElementValueGen
operator|.
name|STRING
argument_list|,
name|cp
argument_list|,
literal|"hello"
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|assertTrue
argument_list|(
literal|"Should have the same index in the constantpool but "
operator|+
name|evg
operator|.
name|getIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|cp
operator|.
name|lookupUtf8
argument_list|(
literal|"hello"
argument_list|)
argument_list|,
name|evg
operator|.
name|getIndex
argument_list|()
operator|==
name|cp
operator|.
name|lookupUtf8
argument_list|(
literal|"hello"
argument_list|)
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
comment|// //
comment|// Create enum element value
specifier|public
name|void
name|testCreateEnumElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|ObjectType
name|enumType
init|=
operator|new
name|ObjectType
argument_list|(
literal|"SimpleEnum"
argument_list|)
decl_stmt|;
comment|// Supports rainbow
comment|// :)
name|EnumElementValueGen
name|evg
init|=
operator|new
name|EnumElementValueGen
argument_list|(
name|enumType
argument_list|,
literal|"Red"
argument_list|,
name|cp
argument_list|)
decl_stmt|;
comment|// Creation of an element like that should leave a new entry in the
comment|// cpool
name|assertTrue
argument_list|(
literal|"The new ElementValue value index should match the contents of the constantpool but "
operator|+
name|evg
operator|.
name|getValueIndex
argument_list|()
operator|+
literal|"!="
operator|+
name|cp
operator|.
name|lookupUtf8
argument_list|(
literal|"Red"
argument_list|)
argument_list|,
name|evg
operator|.
name|getValueIndex
argument_list|()
operator|==
name|cp
operator|.
name|lookupUtf8
argument_list|(
literal|"Red"
argument_list|)
argument_list|)
expr_stmt|;
comment|// BCELBUG: Should the class signature or class name be in the constant
comment|// pool? (see note in ConstantPool)
comment|// assertTrue("The new ElementValue type index should match the contents
comment|// of the constantpool but "+
comment|// evg.getTypeIndex()+"!="+cp.lookupClass(enumType.getSignature()),
comment|// evg.getTypeIndex()==cp.lookupClass(enumType.getSignature()));
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
comment|// //
comment|// Create class element value
specifier|public
name|void
name|testCreateClassElementValue
parameter_list|()
throws|throws
name|Exception
block|{
name|ClassGen
name|cg
init|=
name|createClassGen
argument_list|(
literal|"HelloWorld"
argument_list|)
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|ObjectType
name|classType
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.Integer"
argument_list|)
decl_stmt|;
name|ClassElementValueGen
name|evg
init|=
operator|new
name|ClassElementValueGen
argument_list|(
name|classType
argument_list|,
name|cp
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
literal|"Unexpected value for contained class: '"
operator|+
name|evg
operator|.
name|getClassString
argument_list|()
operator|+
literal|"'"
argument_list|,
name|evg
operator|.
name|getClassString
argument_list|()
operator|.
name|contains
argument_list|(
literal|"Integer"
argument_list|)
argument_list|)
expr_stmt|;
name|checkSerialize
argument_list|(
name|evg
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|checkSerialize
parameter_list|(
specifier|final
name|ElementValueGen
name|evgBefore
parameter_list|,
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|beforeValue
init|=
name|evgBefore
operator|.
name|stringifyValue
argument_list|()
decl_stmt|;
name|ByteArrayOutputStream
name|baos
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
try|try
init|(
name|DataOutputStream
name|dos
init|=
operator|new
name|DataOutputStream
argument_list|(
name|baos
argument_list|)
init|)
block|{
name|evgBefore
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
name|dos
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
name|ElementValueGen
name|evgAfter
decl_stmt|;
try|try
init|(
name|DataInputStream
name|dis
init|=
operator|new
name|DataInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|baos
operator|.
name|toByteArray
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
name|evgAfter
operator|=
name|ElementValueGen
operator|.
name|readElementValue
argument_list|(
name|dis
argument_list|,
name|cpg
argument_list|)
expr_stmt|;
block|}
name|String
name|afterValue
init|=
name|evgAfter
operator|.
name|stringifyValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|beforeValue
operator|.
name|equals
argument_list|(
name|afterValue
argument_list|)
condition|)
block|{
name|fail
argument_list|(
literal|"Deserialization failed: before='"
operator|+
name|beforeValue
operator|+
literal|"' after='"
operator|+
name|afterValue
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

