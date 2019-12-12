import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogActions from '@material-ui/core/DialogActions';
import Dialog from '@material-ui/core/Dialog';
import Input from '@material-ui/core/Input';
import { Typography, Grid } from '@material-ui/core';
import {Zoom} from '@material-ui/core';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Zoom direction="up" ref={ref} {...props} />;
});

class LoginDialog extends React.Component {
    constructor(props) {
        super();
        this.state = {
        };
    }

    handleCancel = () => {
        this.props.onClose();
    };

    handleConfirm = () => {

    }


    render() {
        const { open, classes, onClose, Enable, onLogin } = this.props;

        return (
            <Dialog
                maxWidth="xs"
                aria-labelledby="confirmation-dialog-title"
                open={open}
                TransitionComponent={Transition}
                classes={classes}
                onClose={onClose}
            >
                <DialogTitle id="confirmation-dialog-title">{"欢迎使用供应链金融平台"}</DialogTitle>
                <DialogContent >
                    <Button variant="contained" color="primary" style={{width: "100%"}} >
                        {"使用PEM私钥文件登录"}
                        <Input type="file" id="pemFile" accept=".pem" style={{ opacity: 0, position: "absolute", width: "100%" }} onChange={onLogin} />
                    </Button>
                </DialogContent>
            </Dialog>
        );
    }
}

LoginDialog.propTypes = {
    onClose: PropTypes.func
};

const loginDialog = LoginDialog;
export { loginDialog as LoginDialog }