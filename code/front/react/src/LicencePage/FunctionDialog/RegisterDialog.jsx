import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import Dialog from '@material-ui/core/Dialog';
import { Typography, Grid, TextField } from '@material-ui/core';
import { AccountCircle, AttachMoney } from '@material-ui/icons';
import {Slide} from '@material-ui/core';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

class RegisterDialog extends React.Component {
    constructor(props) {
        super();
        this.state = {
            account: "",
            amount: "",
            Enable: false
        };
    }

    handleText = (e) =>{
        const { account, amount } = this.state;
        const { isMaster } = this.props;
        let enable = isMaster ? !(amount == "" || e.currentTarget.value == "" || parseFloat(amount) < 0) : !(e.currentTarget.value == "");
        this.setState({
            Enable: enable,
            account: e.currentTarget.value
        })
    }

    handleAmount = (e) =>{
        const { account} = this.state;
        this.setState({
            Enable: !(e.currentTarget.value == "" || account == "" || parseFloat(e.currentTarget.value) < 0),
            amount: e.currentTarget.value
        })
    }

    handleCancel = () => {
        location.reload();
    };

    handleConfirm = () => {
        const { isMaster } = this.props;
        const { account, amount } = this.state;
        this.props.onConfirm("account=" + account + "&amount=" + (isMaster ? amount : "0"));
    }



    render() {
        const { open, classes, onClose, isMaster } = this.props;
        const { Enable } = this.state;
        return (
            <Dialog
                maxWidth="xs"
                aria-labelledby="confirmation-dialog-title"
                open={open}
                TransitionComponent={Transition}
                classes={classes}
            >
                <DialogTitle id="confirmation-dialog-title">{"注册"}</DialogTitle>
                <DialogContent>
                    <Typography variant="h5">
                        <Grid container spacing={8} alignItems="flex-end">
                            <Grid item>
                                <AccountCircle />
                            </Grid>
                            <Grid item>
                                <TextField label="账户名称" onChange={this.handleText}/>
                            </Grid>
                        </Grid>
                        {
                            isMaster &&
                            <Grid container spacing={8} alignItems="flex-end">
                                <Grid item>
                                    <AttachMoney />
                                </Grid>
                                <Grid item>
                                    <TextField label="初始账单额度" onChange={this.handleAmount}/>
                                </Grid>
                            </Grid>
                        }

                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose} variant="contained" style={{ width: "40%" }}>
                        {"取消"}
                    </Button>
                    <Button onClick={this.handleConfirm} variant="contained" style={{ width: "40%" }} color="primary" disabled={!Enable}>
                        {"确认"}
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

RegisterDialog.propTypes = {
    title: PropTypes.string,
    text: PropTypes.string,
    onConfirm: PropTypes.func,
    onClose: PropTypes.func
};

const registerDialog = RegisterDialog;
export { registerDialog as RegisterDialog }