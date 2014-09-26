begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_comment
comment|/* Generated By:JJTree: Do not edit this line. ASTProgram.java */
end_comment

begin_comment
comment|/* JJT: 0.3pre1 */
end_comment

begin_package
package|package
name|Mini
package|;
end_package

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
name|generic
operator|.
name|ALOAD
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
name|GETSTATIC
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
name|ILOAD
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
name|INVOKESPECIAL
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
name|INVOKESTATIC
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
name|INVOKEVIRTUAL
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
name|InstructionConstants
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
name|InstructionList
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
name|NEW
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
name|PUSH
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
name|PUTSTATIC
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
name|RETURN
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
comment|/**  * Root node of everything, direct children are nodes of type FunDecl  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|ASTProgram
extends|extends
name|SimpleNode
implements|implements
name|MiniParserConstants
implements|,
name|MiniParserTreeConstants
implements|,
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
block|{
specifier|private
name|ASTFunDecl
index|[]
name|fun_decls
decl_stmt|;
comment|// Children: Function declarations
specifier|private
name|Environment
name|env
decl_stmt|;
comment|// Environment contains variables and functions
name|ASTProgram
parameter_list|(
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|env
operator|=
operator|new
name|Environment
argument_list|()
expr_stmt|;
comment|/* Add predefined functions WRITE/READ.      * WRITE has one arg of type T_INT, both return T_INT.      */
name|ASTIdent
name|ident
init|=
operator|new
name|ASTIdent
argument_list|(
literal|"WRITE"
argument_list|,
name|T_INT
argument_list|,
operator|-
literal|1
argument_list|,
operator|-
literal|1
argument_list|)
decl_stmt|;
name|ASTIdent
index|[]
name|args
init|=
block|{
operator|new
name|ASTIdent
argument_list|(
literal|""
argument_list|,
name|T_INT
argument_list|,
operator|-
literal|1
argument_list|,
operator|-
literal|1
argument_list|)
block|}
decl_stmt|;
name|Function
name|fun
init|=
operator|new
name|Function
argument_list|(
name|ident
argument_list|,
name|args
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|env
operator|.
name|put
argument_list|(
name|fun
argument_list|)
expr_stmt|;
name|ident
operator|=
operator|new
name|ASTIdent
argument_list|(
literal|"READ"
argument_list|,
name|T_INT
argument_list|,
operator|-
literal|1
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|args
operator|=
operator|new
name|ASTIdent
index|[
literal|0
index|]
expr_stmt|;
name|fun
operator|=
operator|new
name|Function
argument_list|(
name|ident
argument_list|,
name|args
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|fun
argument_list|)
expr_stmt|;
comment|/* Add predefined idents TRUE/FALSE of type T_BOOLEAN      */
name|ident
operator|=
operator|new
name|ASTIdent
argument_list|(
literal|"TRUE"
argument_list|,
name|T_BOOLEAN
argument_list|,
operator|-
literal|1
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|Variable
name|var
init|=
operator|new
name|Variable
argument_list|(
name|ident
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|env
operator|.
name|put
argument_list|(
name|var
argument_list|)
expr_stmt|;
name|ident
operator|=
operator|new
name|ASTIdent
argument_list|(
literal|"FALSE"
argument_list|,
name|T_BOOLEAN
argument_list|,
operator|-
literal|1
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|var
operator|=
operator|new
name|Variable
argument_list|(
name|ident
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|var
argument_list|)
expr_stmt|;
block|}
name|ASTProgram
parameter_list|(
name|MiniParser
name|p
parameter_list|,
name|int
name|id
parameter_list|)
block|{
name|super
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Node
name|jjtCreate
parameter_list|(
name|MiniParser
name|p
parameter_list|,
name|int
name|id
parameter_list|)
block|{
return|return
operator|new
name|ASTProgram
argument_list|(
name|p
argument_list|,
name|id
argument_list|)
return|;
block|}
comment|/**    * Overrides SimpleNode.closeNode().    * Cast children to appropiate type.    */
annotation|@
name|Override
specifier|public
name|void
name|closeNode
parameter_list|()
block|{
if|if
condition|(
name|children
operator|!=
literal|null
condition|)
block|{
comment|// Non-empty program ?
name|fun_decls
operator|=
operator|new
name|ASTFunDecl
index|[
name|children
operator|.
name|length
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|children
argument_list|,
literal|0
argument_list|,
name|fun_decls
argument_list|,
literal|0
argument_list|,
name|children
operator|.
name|length
argument_list|)
expr_stmt|;
name|children
operator|=
literal|null
expr_stmt|;
comment|// Throw away old reference
block|}
block|}
comment|/**    * First pass of parse tree.    *    * Put everything into the environment, which is copied appropiately to each    * recursion level, i.e. each FunDecl gets its own copy that it can further    * manipulate.     *    * Checks for name clashes of function declarations.    */
specifier|public
name|ASTProgram
name|traverse
parameter_list|()
block|{
name|ASTFunDecl
name|f
decl_stmt|;
name|ASTIdent
name|name
decl_stmt|;
name|String
name|fname
decl_stmt|;
name|EnvEntry
name|fun
decl_stmt|;
name|Function
name|main
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|fun_decls
operator|!=
literal|null
condition|)
block|{
comment|// Put function names into hash table aka. environment
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|f
operator|=
name|fun_decls
index|[
name|i
index|]
expr_stmt|;
name|name
operator|=
name|f
operator|.
name|getName
argument_list|()
expr_stmt|;
name|fname
operator|=
name|name
operator|.
name|getName
argument_list|()
expr_stmt|;
name|fun
operator|=
name|env
operator|.
name|get
argument_list|(
name|fname
argument_list|)
expr_stmt|;
comment|// Lookup in env
if|if
condition|(
name|fun
operator|!=
literal|null
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|f
operator|.
name|getLine
argument_list|()
argument_list|,
name|f
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Redeclaration of "
operator|+
name|fun
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|env
operator|.
name|put
argument_list|(
operator|new
name|Function
argument_list|(
name|name
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// `args' will be set by FunDecl.traverse()
block|}
block|}
comment|// Go for it
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fun_decls
index|[
name|i
index|]
operator|=
name|fun_decls
index|[
name|i
index|]
operator|.
name|traverse
argument_list|(
operator|(
name|Environment
operator|)
name|env
operator|.
name|clone
argument_list|()
argument_list|)
expr_stmt|;
comment|// Look for `main' routine
name|fname
operator|=
name|fun_decls
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|fname
operator|.
name|equals
argument_list|(
literal|"main"
argument_list|)
condition|)
block|{
name|main
operator|=
operator|(
name|Function
operator|)
name|env
operator|.
name|get
argument_list|(
name|fname
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|main
operator|==
literal|null
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|"You didn't declare a `main' function."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|main
operator|.
name|getNoArgs
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|main
operator|.
name|getLine
argument_list|()
argument_list|,
name|main
operator|.
name|getColumn
argument_list|()
argument_list|,
literal|"Main function has too many arguments declared."
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|this
return|;
block|}
comment|/**     * Second pass, determine type of each node, if possible.    */
specifier|public
name|void
name|eval
parameter_list|(
name|int
name|pass
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fun_decls
index|[
name|i
index|]
operator|.
name|eval
argument_list|(
name|pass
argument_list|)
expr_stmt|;
if|if
condition|(
name|pass
operator|==
literal|3
condition|)
block|{
comment|// Final check for unresolved types
name|ASTIdent
name|name
init|=
name|fun_decls
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|getType
argument_list|()
operator|==
name|T_UNKNOWN
condition|)
block|{
name|MiniC
operator|.
name|addError
argument_list|(
name|name
operator|.
name|getColumn
argument_list|()
argument_list|,
name|name
operator|.
name|getLine
argument_list|()
argument_list|,
literal|"Type of function "
operator|+
name|name
operator|.
name|getName
argument_list|()
operator|+
literal|" can not be determined (infinite recursion?)."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**    * Fifth pass, produce Java code.    */
specifier|public
name|void
name|code
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"import java.io.BufferedReader;"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"import java.io.InputStreamReader;"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"import java.io.IOException;\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"public final class "
operator|+
name|name
operator|+
literal|" {"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"  private static BufferedReader _in = new BufferedReader"
operator|+
literal|"(new InputStreamReader(System.in));\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"  private static int _readInt() throws IOException {\n"
operator|+
literal|"    System.out.print(\"Please enter a number> \");\n"
operator|+
literal|"    return Integer.parseInt(_in.readLine());\n  }\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"  private static int _writeInt(int n) {\n"
operator|+
literal|"    System.out.println(\"Result: \" + n);\n    return 0;\n  }\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fun_decls
index|[
name|i
index|]
operator|.
name|code
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
block|}
comment|/**    * Fifth pass, produce Java byte code.    */
specifier|public
name|void
name|byte_code
parameter_list|(
name|ClassGen
name|class_gen
parameter_list|,
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
comment|/* private static BufferedReader _in;      */
name|class_gen
operator|.
name|addField
argument_list|(
operator|new
name|Field
argument_list|(
name|ACC_PRIVATE
operator||
name|ACC_STATIC
argument_list|,
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"_in"
argument_list|)
argument_list|,
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"Ljava/io/BufferedReader;"
argument_list|)
argument_list|,
literal|null
argument_list|,
name|cp
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|MethodGen
name|method
decl_stmt|;
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
name|String
name|class_name
init|=
name|class_gen
operator|.
name|getClassName
argument_list|()
decl_stmt|;
comment|/* Often used constant pool entries      */
name|int
name|_in
init|=
name|cp
operator|.
name|addFieldref
argument_list|(
name|class_name
argument_list|,
literal|"_in"
argument_list|,
literal|"Ljava/io/BufferedReader;"
argument_list|)
decl_stmt|;
name|int
name|out
init|=
name|cp
operator|.
name|addFieldref
argument_list|(
literal|"java.lang.System"
argument_list|,
literal|"out"
argument_list|,
literal|"Ljava/io/PrintStream;"
argument_list|)
decl_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|"Please enter a number> "
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.PrintStream"
argument_list|,
literal|"print"
argument_list|,
literal|"(Ljava/lang/String;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|_in
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.BufferedReader"
argument_list|,
literal|"readLine"
argument_list|,
literal|"()Ljava/lang/String;"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESTATIC
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.lang.Integer"
argument_list|,
literal|"parseInt"
argument_list|,
literal|"(Ljava/lang/String;)I"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|IRETURN
argument_list|)
expr_stmt|;
comment|/* private static int _readInt() throws IOException      */
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_STATIC
operator||
name|ACC_PRIVATE
operator||
name|ACC_FINAL
argument_list|,
name|Type
operator|.
name|INT
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
literal|null
argument_list|,
literal|"_readInt"
argument_list|,
name|class_name
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|method
operator|.
name|addException
argument_list|(
literal|"java.io.IOException"
argument_list|)
expr_stmt|;
name|method
operator|.
name|setMaxStack
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|class_gen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
comment|/* private static int _writeInt(int i) throws IOException      */
name|Type
index|[]
name|args
init|=
block|{
name|Type
operator|.
name|INT
block|}
decl_stmt|;
name|String
index|[]
name|argv
init|=
block|{
literal|"i"
block|}
decl_stmt|;
name|il
operator|=
operator|new
name|InstructionList
argument_list|()
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|NEW
argument_list|(
name|cp
operator|.
name|addClass
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|DUP
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|"Result: "
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESPECIAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|,
literal|"<init>"
argument_list|,
literal|"(Ljava/lang/String;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ILOAD
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|,
literal|"append"
argument_list|,
literal|"(I)Ljava/lang/StringBuffer;"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|,
literal|"toString"
argument_list|,
literal|"()Ljava/lang/String;"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.PrintStream"
argument_list|,
literal|"println"
argument_list|,
literal|"(Ljava/lang/String;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|IRETURN
argument_list|)
expr_stmt|;
comment|// Reuse objects, if possible
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_STATIC
operator||
name|ACC_PRIVATE
operator||
name|ACC_FINAL
argument_list|,
name|Type
operator|.
name|INT
argument_list|,
name|args
argument_list|,
name|argv
argument_list|,
literal|"_writeInt"
argument_list|,
name|class_name
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|method
operator|.
name|setMaxStack
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|class_gen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
comment|/* public<init> -- constructor      */
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// Dispose instruction handles for better memory utilization
name|il
operator|=
operator|new
name|InstructionList
argument_list|()
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ALOAD
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Push `this'
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESPECIAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.lang.Object"
argument_list|,
literal|"<init>"
argument_list|,
literal|"()V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|RETURN
argument_list|()
argument_list|)
expr_stmt|;
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_PUBLIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
literal|null
argument_list|,
literal|"<init>"
argument_list|,
name|class_name
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|method
operator|.
name|setMaxStack
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|class_gen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
comment|/* class initializer      */
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// Dispose instruction handles for better memory utilization
name|il
operator|=
operator|new
name|InstructionList
argument_list|()
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|NEW
argument_list|(
name|cp
operator|.
name|addClass
argument_list|(
literal|"java.io.BufferedReader"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|DUP
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|NEW
argument_list|(
name|cp
operator|.
name|addClass
argument_list|(
literal|"java.io.InputStreamReader"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|DUP
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|cp
operator|.
name|addFieldref
argument_list|(
literal|"java.lang.System"
argument_list|,
literal|"in"
argument_list|,
literal|"Ljava/io/InputStream;"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESPECIAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.InputStreamReader"
argument_list|,
literal|"<init>"
argument_list|,
literal|"(Ljava/io/InputStream;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKESPECIAL
argument_list|(
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.BufferedReader"
argument_list|,
literal|"<init>"
argument_list|,
literal|"(Ljava/io/Reader;)V"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUTSTATIC
argument_list|(
name|_in
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|RETURN
argument_list|)
expr_stmt|;
comment|// Reuse instruction constants
name|method
operator|=
operator|new
name|MethodGen
argument_list|(
name|ACC_STATIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
literal|null
argument_list|,
literal|"<clinit>"
argument_list|,
name|class_name
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|method
operator|.
name|setMaxStack
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|class_gen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fun_decls
index|[
name|i
index|]
operator|.
name|byte_code
argument_list|(
name|class_gen
argument_list|,
name|cp
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|toString
argument_list|(
name|prefix
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fun_decls
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|fun_decls
index|[
name|i
index|]
operator|.
name|dump
argument_list|(
name|prefix
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

