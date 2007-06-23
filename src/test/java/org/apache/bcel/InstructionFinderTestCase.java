begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|util
operator|.
name|Iterator
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
name|AbstractTestCase
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
name|generic
operator|.
name|InstructionHandle
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
name|util
operator|.
name|InstructionFinder
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
name|InstructionFinder
operator|.
name|CodeConstraint
import|;
end_import

begin_class
specifier|public
class|class
name|InstructionFinderTestCase
extends|extends
name|AbstractTestCase
block|{
specifier|public
name|void
name|testSearchAll
parameter_list|()
throws|throws
name|Exception
block|{
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
literal|"org.apache.bcel.util.InstructionFinder"
argument_list|)
decl_stmt|;
name|Method
index|[]
name|methods
init|=
name|clazz
operator|.
name|getMethods
argument_list|()
decl_stmt|;
name|Method
name|searchM
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Method
name|m
range|:
name|methods
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"search"
argument_list|)
operator|&&
operator|(
name|m
operator|.
name|getArgumentTypes
argument_list|()
operator|.
name|length
operator|==
literal|3
operator|)
condition|)
block|{
name|searchM
operator|=
name|m
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|searchM
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"search method not found"
argument_list|)
throw|;
name|byte
index|[]
name|bytes
init|=
name|searchM
operator|.
name|getCode
argument_list|()
operator|.
name|getCode
argument_list|()
decl_stmt|;
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|(
name|bytes
argument_list|)
decl_stmt|;
name|InstructionFinder
name|finder
init|=
operator|new
name|InstructionFinder
argument_list|(
name|il
argument_list|)
decl_stmt|;
name|Iterator
name|it
init|=
name|finder
operator|.
name|search
argument_list|(
literal|".*"
argument_list|,
name|il
operator|.
name|getStart
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|InstructionHandle
index|[]
name|ihs
init|=
operator|(
name|InstructionHandle
index|[]
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
for|for
control|(
name|InstructionHandle
name|ih
range|:
name|ihs
control|)
block|{
name|size
operator|+=
name|ih
operator|.
name|getInstruction
argument_list|()
operator|.
name|getLength
argument_list|()
expr_stmt|;
block|}
name|assertEquals
argument_list|(
name|bytes
operator|.
name|length
argument_list|,
name|size
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

