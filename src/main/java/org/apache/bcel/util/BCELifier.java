begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
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
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|Const
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
name|ClassParser
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
name|ConstantValue
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
name|Field
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
name|Utility
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
name|ArrayType
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
name|MethodGen
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
name|Type
import|;
end_import

begin_comment
comment|/**  * This class takes a given JavaClass object and converts it to a  * Java program that creates that very class using BCEL. This  * gives new users of BCEL a useful example showing how things  * are done with BCEL. It does not cover all features of BCEL,  * but tries to mimic hand-written code as close as possible.  *  */
end_comment

begin_class
specifier|public
class|class
name|BCELifier
extends|extends
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|EmptyVisitor
block|{
comment|/**      * Enum corresponding to flag source.      */
specifier|public
enum|enum
name|FLAGS
block|{
name|UNKNOWN
block|,
name|CLASS
block|,
name|METHOD
block|,     }
comment|// The base package name for imports; assumes Const is at the top level
comment|// N.B we use the class so renames will be detected by the compiler/IDE
specifier|private
specifier|static
specifier|final
name|String
name|BASE_PACKAGE
init|=
name|Const
operator|.
name|class
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONSTANT_PREFIX
init|=
name|Const
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"."
decl_stmt|;
specifier|private
specifier|final
name|JavaClass
name|_clazz
decl_stmt|;
specifier|private
specifier|final
name|PrintWriter
name|_out
decl_stmt|;
specifier|private
specifier|final
name|ConstantPoolGen
name|_cp
decl_stmt|;
comment|/** @param clazz Java class to "decompile"      * @param out where to output Java program      */
specifier|public
name|BCELifier
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|,
specifier|final
name|OutputStream
name|out
parameter_list|)
block|{
name|_clazz
operator|=
name|clazz
expr_stmt|;
name|_out
operator|=
operator|new
name|PrintWriter
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|_cp
operator|=
operator|new
name|ConstantPoolGen
argument_list|(
name|_clazz
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/** Start Java code generation      */
specifier|public
name|void
name|start
parameter_list|()
block|{
name|visitJavaClass
argument_list|(
name|_clazz
argument_list|)
expr_stmt|;
name|_out
operator|.
name|flush
argument_list|()
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
name|clazz
parameter_list|)
block|{
name|String
name|class_name
init|=
name|clazz
operator|.
name|getClassName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|super_name
init|=
name|clazz
operator|.
name|getSuperclassName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|package_name
init|=
name|clazz
operator|.
name|getPackageName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|inter
init|=
name|Utility
operator|.
name|printArray
argument_list|(
name|clazz
operator|.
name|getInterfaceNames
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|package_name
argument_list|)
condition|)
block|{
name|class_name
operator|=
name|class_name
operator|.
name|substring
argument_list|(
name|package_name
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"package "
operator|+
name|package_name
operator|+
literal|";"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
name|_out
operator|.
name|println
argument_list|(
literal|"import "
operator|+
name|BASE_PACKAGE
operator|+
literal|".generic.*;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"import "
operator|+
name|BASE_PACKAGE
operator|+
literal|".classfile.*;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"import "
operator|+
name|BASE_PACKAGE
operator|+
literal|".*;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"import java.io.*;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"public class "
operator|+
name|class_name
operator|+
literal|"Creator {"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  private InstructionFactory _factory;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  private ConstantPoolGen    _cp;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  private ClassGen           _cg;"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  public "
operator|+
name|class_name
operator|+
literal|"Creator() {"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    _cg = new ClassGen(\""
operator|+
operator|(
operator|(
literal|""
operator|.
name|equals
argument_list|(
name|package_name
argument_list|)
operator|)
condition|?
name|class_name
else|:
name|package_name
operator|+
literal|"."
operator|+
name|class_name
operator|)
operator|+
literal|"\", \""
operator|+
name|super_name
operator|+
literal|"\", "
operator|+
literal|"\""
operator|+
name|clazz
operator|.
name|getSourceFileName
argument_list|()
operator|+
literal|"\", "
operator|+
name|printFlags
argument_list|(
name|clazz
operator|.
name|getAccessFlags
argument_list|()
argument_list|,
name|FLAGS
operator|.
name|CLASS
argument_list|)
operator|+
literal|", "
operator|+
literal|"new String[] { "
operator|+
name|inter
operator|+
literal|" });"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    _cp = _cg.getConstantPool();"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    _factory = new InstructionFactory(_cg, _cp);"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  }"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
name|printCreate
argument_list|()
expr_stmt|;
specifier|final
name|Field
index|[]
name|fields
init|=
name|clazz
operator|.
name|getFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|fields
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|_out
operator|.
name|println
argument_list|(
literal|"  private void createFields() {"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    FieldGen field;"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|Field
name|field
range|:
name|fields
control|)
block|{
name|field
operator|.
name|accept
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
name|_out
operator|.
name|println
argument_list|(
literal|"  }"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
specifier|final
name|Method
index|[]
name|methods
init|=
name|clazz
operator|.
name|getMethods
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|methods
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|_out
operator|.
name|println
argument_list|(
literal|"  private void createMethod_"
operator|+
name|i
operator|+
literal|"() {"
argument_list|)
expr_stmt|;
name|methods
index|[
name|i
index|]
operator|.
name|accept
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  }"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
name|printMain
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|printCreate
parameter_list|()
block|{
name|_out
operator|.
name|println
argument_list|(
literal|"  public void create(OutputStream out) throws IOException {"
argument_list|)
expr_stmt|;
specifier|final
name|Field
index|[]
name|fields
init|=
name|_clazz
operator|.
name|getFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|fields
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|_out
operator|.
name|println
argument_list|(
literal|"    createFields();"
argument_list|)
expr_stmt|;
block|}
specifier|final
name|Method
index|[]
name|methods
init|=
name|_clazz
operator|.
name|getMethods
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|methods
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|_out
operator|.
name|println
argument_list|(
literal|"    createMethod_"
operator|+
name|i
operator|+
literal|"();"
argument_list|)
expr_stmt|;
block|}
name|_out
operator|.
name|println
argument_list|(
literal|"    _cg.getJavaClass().dump(out);"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  }"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|printMain
parameter_list|()
block|{
specifier|final
name|String
name|class_name
init|=
name|_clazz
operator|.
name|getClassName
argument_list|()
decl_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  public static void main(String[] args) throws Exception {"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    "
operator|+
name|class_name
operator|+
literal|"Creator creator = new "
operator|+
name|class_name
operator|+
literal|"Creator();"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    creator.create(new FileOutputStream(\""
operator|+
name|class_name
operator|+
literal|".class\"));"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"  }"
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
name|field
parameter_list|)
block|{
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    field = new FieldGen("
operator|+
name|printFlags
argument_list|(
name|field
operator|.
name|getAccessFlags
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
name|printType
argument_list|(
name|field
operator|.
name|getSignature
argument_list|()
argument_list|)
operator|+
literal|", \""
operator|+
name|field
operator|.
name|getName
argument_list|()
operator|+
literal|"\", _cp);"
argument_list|)
expr_stmt|;
specifier|final
name|ConstantValue
name|cv
init|=
name|field
operator|.
name|getConstantValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|cv
operator|!=
literal|null
condition|)
block|{
specifier|final
name|String
name|value
init|=
name|cv
operator|.
name|toString
argument_list|()
decl_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    field.setInitValue("
operator|+
name|value
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
name|_out
operator|.
name|println
argument_list|(
literal|"    _cg.addField(field.getField());"
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
name|method
parameter_list|)
block|{
specifier|final
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|method
argument_list|,
name|_clazz
operator|.
name|getClassName
argument_list|()
argument_list|,
name|_cp
argument_list|)
decl_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    InstructionList il = new InstructionList();"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    MethodGen method = new MethodGen("
operator|+
name|printFlags
argument_list|(
name|method
operator|.
name|getAccessFlags
argument_list|()
argument_list|,
name|FLAGS
operator|.
name|METHOD
argument_list|)
operator|+
literal|", "
operator|+
name|printType
argument_list|(
name|mg
operator|.
name|getReturnType
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
name|printArgumentTypes
argument_list|(
name|mg
operator|.
name|getArgumentTypes
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
literal|"new String[] { "
operator|+
name|Utility
operator|.
name|printArray
argument_list|(
name|mg
operator|.
name|getArgumentNames
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
operator|+
literal|" }, \""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\", \""
operator|+
name|_clazz
operator|.
name|getClassName
argument_list|()
operator|+
literal|"\", il, _cp);"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|()
expr_stmt|;
specifier|final
name|BCELFactory
name|factory
init|=
operator|new
name|BCELFactory
argument_list|(
name|mg
argument_list|,
name|_out
argument_list|)
decl_stmt|;
name|factory
operator|.
name|start
argument_list|()
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    method.setMaxStack();"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    method.setMaxLocals();"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    _cg.addMethod(method.getMethod());"
argument_list|)
expr_stmt|;
name|_out
operator|.
name|println
argument_list|(
literal|"    il.dispose();"
argument_list|)
expr_stmt|;
block|}
specifier|static
name|String
name|printFlags
parameter_list|(
specifier|final
name|int
name|flags
parameter_list|)
block|{
return|return
name|printFlags
argument_list|(
name|flags
argument_list|,
name|FLAGS
operator|.
name|UNKNOWN
argument_list|)
return|;
block|}
comment|/**      * Return a string with the flag settings      * @param flags the flags field to interpret      * @param location the item type      * @return the formatted string      * @since 6.0 made public      */
specifier|public
specifier|static
name|String
name|printFlags
parameter_list|(
specifier|final
name|int
name|flags
parameter_list|,
specifier|final
name|FLAGS
name|location
parameter_list|)
block|{
if|if
condition|(
name|flags
operator|==
literal|0
condition|)
block|{
return|return
literal|"0"
return|;
block|}
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|pow
init|=
literal|1
init|;
name|pow
operator|<=
name|Const
operator|.
name|MAX_ACC_FLAG
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|flags
operator|&
name|pow
operator|)
operator|!=
literal|0
condition|)
block|{
if|if
condition|(
operator|(
name|pow
operator|==
name|Const
operator|.
name|ACC_SYNCHRONIZED
operator|)
operator|&&
operator|(
name|location
operator|==
name|FLAGS
operator|.
name|CLASS
operator|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|CONSTANT_PREFIX
operator|+
literal|"ACC_SUPER | "
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|pow
operator|==
name|Const
operator|.
name|ACC_VOLATILE
operator|)
operator|&&
operator|(
name|location
operator|==
name|FLAGS
operator|.
name|METHOD
operator|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|CONSTANT_PREFIX
operator|+
literal|"ACC_BRIDGE | "
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|(
name|pow
operator|==
name|Const
operator|.
name|ACC_TRANSIENT
operator|)
operator|&&
operator|(
name|location
operator|==
name|FLAGS
operator|.
name|METHOD
operator|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|CONSTANT_PREFIX
operator|+
literal|"ACC_VARARGS | "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|i
operator|<
name|Const
operator|.
name|ACCESS_NAMES_LENGTH
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|CONSTANT_PREFIX
operator|+
literal|"ACC_"
argument_list|)
operator|.
name|append
argument_list|(
name|Const
operator|.
name|getAccessName
argument_list|(
name|i
argument_list|)
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|" | "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|buf
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
name|CONSTANT_PREFIX
operator|+
literal|"ACC_BIT %x | "
argument_list|,
name|pow
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|pow
operator|<<=
literal|1
expr_stmt|;
block|}
specifier|final
name|String
name|str
init|=
name|buf
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|str
operator|.
name|length
argument_list|()
operator|-
literal|3
argument_list|)
return|;
block|}
specifier|static
name|String
name|printArgumentTypes
parameter_list|(
specifier|final
name|Type
index|[]
name|arg_types
parameter_list|)
block|{
if|if
condition|(
name|arg_types
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|"Type.NO_ARGS"
return|;
block|}
specifier|final
name|StringBuilder
name|args
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|arg_types
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|args
operator|.
name|append
argument_list|(
name|printType
argument_list|(
name|arg_types
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|arg_types
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|args
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|"new Type[] { "
operator|+
name|args
operator|.
name|toString
argument_list|()
operator|+
literal|" }"
return|;
block|}
specifier|static
name|String
name|printType
parameter_list|(
specifier|final
name|Type
name|type
parameter_list|)
block|{
return|return
name|printType
argument_list|(
name|type
operator|.
name|getSignature
argument_list|()
argument_list|)
return|;
block|}
specifier|static
name|String
name|printType
parameter_list|(
specifier|final
name|String
name|signature
parameter_list|)
block|{
specifier|final
name|Type
name|type
init|=
name|Type
operator|.
name|getType
argument_list|(
name|signature
argument_list|)
decl_stmt|;
specifier|final
name|byte
name|t
init|=
name|type
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|t
operator|<=
name|Const
operator|.
name|T_VOID
condition|)
block|{
return|return
literal|"Type."
operator|+
name|Const
operator|.
name|getTypeName
argument_list|(
name|t
argument_list|)
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
return|;
block|}
if|else if
condition|(
name|type
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"java.lang.String"
argument_list|)
condition|)
block|{
return|return
literal|"Type.STRING"
return|;
block|}
if|else if
condition|(
name|type
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"java.lang.Object"
argument_list|)
condition|)
block|{
return|return
literal|"Type.OBJECT"
return|;
block|}
if|else if
condition|(
name|type
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|)
condition|)
block|{
return|return
literal|"Type.STRINGBUFFER"
return|;
block|}
if|else if
condition|(
name|type
operator|instanceof
name|ArrayType
condition|)
block|{
specifier|final
name|ArrayType
name|at
init|=
operator|(
name|ArrayType
operator|)
name|type
decl_stmt|;
return|return
literal|"new ArrayType("
operator|+
name|printType
argument_list|(
name|at
operator|.
name|getBasicType
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
name|at
operator|.
name|getDimensions
argument_list|()
operator|+
literal|")"
return|;
block|}
else|else
block|{
return|return
literal|"new ObjectType(\""
operator|+
name|Utility
operator|.
name|signatureToString
argument_list|(
name|signature
argument_list|,
literal|false
argument_list|)
operator|+
literal|"\")"
return|;
block|}
block|}
comment|/** Default main method      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|argv
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|argv
operator|.
name|length
operator|!=
literal|1
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Usage: BCELifier classname"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\tThe class must exist on the classpath"
argument_list|)
expr_stmt|;
return|return;
block|}
specifier|final
name|JavaClass
name|java_class
init|=
name|getJavaClass
argument_list|(
name|argv
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
specifier|final
name|BCELifier
name|bcelifier
init|=
operator|new
name|BCELifier
argument_list|(
name|java_class
argument_list|,
name|System
operator|.
name|out
argument_list|)
decl_stmt|;
name|bcelifier
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// Needs to be accessible from unit test code
specifier|static
name|JavaClass
name|getJavaClass
parameter_list|(
specifier|final
name|String
name|name
parameter_list|)
throws|throws
name|ClassNotFoundException
throws|,
name|IOException
block|{
name|JavaClass
name|java_class
decl_stmt|;
if|if
condition|(
operator|(
name|java_class
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|name
argument_list|)
operator|)
operator|==
literal|null
condition|)
block|{
name|java_class
operator|=
operator|new
name|ClassParser
argument_list|(
name|name
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
comment|// May throw IOException
block|}
return|return
name|java_class
return|;
block|}
block|}
end_class

end_unit

