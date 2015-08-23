begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
package|;
end_package

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

begin_class
specifier|public
class|class
name|BranchHandleTestCase
block|{
comment|// Test that setInstruction only allows BranchInstructions
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|ClassGenException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testsetInstructionNull
parameter_list|()
block|{
name|BranchHandle
name|bh
init|=
name|BranchHandle
operator|.
name|getBranchHandle
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
comment|// have to start with a valid BI
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
name|bh
operator|.
name|setInstruction
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testsetInstructionBI
parameter_list|()
block|{
name|BranchHandle
name|bh
init|=
name|BranchHandle
operator|.
name|getBranchHandle
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
comment|// have to start with a valid BI
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
name|bh
operator|.
name|setInstruction
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|ClassGenException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testsetInstructionnotBI
parameter_list|()
block|{
name|BranchHandle
name|bh
init|=
name|BranchHandle
operator|.
name|getBranchHandle
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
comment|// have to start with a valid BI
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
name|bh
operator|.
name|setInstruction
argument_list|(
operator|new
name|NOP
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|bh
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|ClassGenException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testGetBHnull
parameter_list|()
block|{
name|BranchHandle
operator|.
name|getBranchHandle
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

