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

class InfoDialog extends React.Component {
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
        const { open, classes, onClose, Enable } = this.props;

        return (
            <Dialog
                maxWidth="xs"
                aria-labelledby="confirmation-dialog-title"
                open={open}
                TransitionComponent={Transition}
                classes={classes}
                onClose={onClose}
            >
                <DialogTitle id="confirmation-dialog-title">{"开发信息"}</DialogTitle>
                <DialogContent >
                    <Grid container direction="column" justify="center">
                        <Grid item>
                            <Typography variant="h6">
                                {"版本：1.0.0"}
                            </Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="h6">
                                {"作者：张涵健"}
                            </Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="h6">
                                {"日期：2019-12-11"}
                            </Typography>
                        </Grid>
                    </Grid>
                </DialogContent>
            </Dialog>
        );
    }
}

InfoDialog.propTypes = {
    onClose: PropTypes.func
};

const infoDialog = InfoDialog;
export { infoDialog as InfoDialog }