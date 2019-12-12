import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { CssBaseline, Drawer, AppBar, Toolbar, Hidden, Typography, Divider, Grid, IconButton, Button, Collapse, Snackbar, TextField } from '@material-ui/core'
import MenuIcon from '@material-ui/icons/Menu';
import { injectIntl } from "react-intl";
import { userService } from '../_services';
import { Card, CardActions, CardContent } from '@material-ui/core';
import Input from '@material-ui/core/Input';
import { ExpandLess, ExpandMore, ExitToApp, AccountCircle, AttachMoney, Search } from '@material-ui/icons';
import GitHubIcon from '@material-ui/icons/GitHub';
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import { MySnackbar } from './FunctionDialog';
import PropTypes from "prop-types";
import { LoginDialog, RegisterDialog, InfoDialog } from "./FunctionDialog";
import { TransferCard, PayCard, SearchCard } from './Cards';
import VpnKeyOutlinedIcon from '@material-ui/icons/VpnKeyOutlined';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';


const drawerWidth = 0;
const styles = theme => ({
    card: {
        minWidth: 600,
    },

    title: {
        margin: 0,
        paddingTop: theme.spacing.unit * 1.5,
        paddingBottom: theme.spacing.unit * 1.5,
    },
    root: {
        display: 'flex',
        overflow: 'hidden',
        width: "100%",
        height: "100%",
        position: "absolute",
    },
    username: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit * 2,
    },
    drawer: {
        background: "#444",
        [theme.breakpoints.up('sm')]: {
            width: drawerWidth,
            flexShrink: 0,
        },
    },
    appBar: {
        //background: "-webkit-linear-gradient(bottom right, #536976 , #292e49 60%)",
        marginLeft: drawerWidth,
        [theme.breakpoints.up('sm')]: {
            width: `calc(100% - ${drawerWidth}px)`,
        },
    },
    menuButton: {
        marginRight: 20,
        [theme.breakpoints.up('sm')]: {
            display: 'none',
        },
    },
    toolbar: theme.mixins.toolbar,
    drawerPaper: {
        width: drawerWidth
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 1.5,
    },
    formControl: {
        padding: theme.spacing.unit,
    },
    button: {

        margin: theme.spacing.unit * 0.5,
        padding: theme.spacing.unit,
        textTransform: 'none',
    },
    iconsmall: {
        fontSize: 20,
        marginRight: 10
    },
    paper: {
        width: '100%',
        maxHeight: 835,
    },
    fullContainer: {
        width: "100%",
        height: "500px"
    },
    background: {
        display: 'flex',
        overflow: 'hidden',
        width: "100%",
        height: "100%",
        position: "absolute",
    }
});



class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pemFile: null,
            loginS: false,
            needRegister: false,
            anchorEl: null,
            isMaster: false,
            hasSearch: false,
            showInfo: false,
            searchData: {
                status: 0,
                amount: 1000,
            },
            snackbarInfo: {
                open: false,
                varient: "success",
                message: "Welcome!"
            },
        };
    }

    handlePem = (event) => {
        let file = event.currentTarget.files[0];
        let FuncList = this;
        const formdata = new FormData();
        formdata.append('file', file);
        userService.login(formdata).then((e) => {
            console.log(e);
            if (e["status"] == 1 || e["status"] == 3) {
                FuncList.setState({
                    loginS: true,
                    needRegister: false,
                    isMaster: e["status"] == 3,
                    snackbarInfo: {
                        open: true,
                        varient: "success",
                        message: "上传私钥成功！"
                    }
                })
            } else {
                FuncList.setState({
                    loginS: true,
                    needRegister: true,
                    isMaster: e["status"] == 2,
                    snackbarInfo: {
                        open: true,
                        varient: "success",
                        message: "上传私钥成功！"
                    }
                })
            }

        })

        event.currentTarget.value = "";
    }


    closeSnackBar = () => {
        const { snackbarInfo } = this.state;
        //console.log(snackbarInfo);
        this.setState({
            snackbarInfo: {
                open: false,
                varient: snackbarInfo.varient,
                message: snackbarInfo.message
            }
        })
    }

    snackbar = () => {
        return (
            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'right',
                }}
                open={this.state.snackbarInfo.open}
                autoHideDuration={3000}
                onClose={this.closeSnackBar}
            >
                <MySnackbar
                    onClose={this.handleClose}
                    variant={this.state.snackbarInfo.varient}
                    message={this.state.snackbarInfo.message}
                    onClose={this.closeSnackBar}
                />
            </Snackbar>
        )
    }

    handleSearch = (e) => {
        let FuncList = this;
        userService.search(e).then(response=>{
            FuncList.setState({
                searchData: response,
                hasSearch: true,
                snackbarInfo: {
                    open: true,
                    varient: response.status == 0 ? "success": "error",
                    message: response.status == 0 ? "查询成功": "用户不存在"
                }
            })
        })
    }

    handleTransfer = (e) =>{
        let FuncList = this;
        userService.transfer(e).then(response=>{
            let status = "error";
            let text = "";
            if (response.status == 0){
                status = "success";
                text = "转让成功";
            } else if (response.status == -2){
                text = "用户不存在";
            } else if (response.status == -3){
                text = "应收账款余额不足";
            } else if (response.status == -4){
                text = "应收账款溢出";
            } else{
                text = "其他错误";
            }
            FuncList.setState({
                snackbarInfo: {
                    open: true,
                    varient: status,
                    message: text
                }
            })
        })
    }

    handlePay = (e) => {
        let FuncList = this;
        userService.pay().then(response=>{
            FuncList.setState({
                snackbarInfo: {
                    open: true,
                    varient: "success",
                    message: "支付成功"
                }
            })
        })
    }

    handleRegister = (e) => {
        let FuncList = this;
        userService.register(e).then(response=>{
            let status = "error";
            let text = "";
            let needRegister = true;
            if (response.status == 0){
                status = "success";
                text = "普通企业注册成功";
                needRegister = false;
            } else if (response.status == 1){
                status = "success";
                text = "核心企业注册成功";
                needRegister = false;
            } else if (response.status == -1){
                text = "用户名已存在";
            } else{
                text = "其他错误";
            }
            console.log()
            FuncList.setState({
                needRegister: needRegister,
                snackbarInfo: {
                    open: true,
                    varient: status,
                    message: text
                }
            })
        })
    }

    render() {
        const { classes, theme } = this.props;
        const { loginS, needRegister, isMaster, searchData, hasSearch, showInfo } = this.state;
        return (
            <div className={classes.root}>
                <CssBaseline />
                {this.snackbar()}
                <LoginDialog open={!loginS} onClose={() => { }} onLogin={this.handlePem} />
                <InfoDialog open={showInfo} onClose={()=>this.setState({showInfo: false})}/>
                <RegisterDialog open={needRegister} isMaster={isMaster} onClose={() => this.setState({ loginS: false, needRegister: false })} onConfirm={this.handleRegister} />
                <AppBar position="fixed" className={classes.appBar} color="primary">
                    <Toolbar>

                        <Grid container direction="row" justify="flex-end" alignItems="center">
                            <Grid item>
                                <IconButton variant="contained" color="secondary" className={classes.button} onClick={() => this.setState({ showInfo: true })}>
                                    <InfoOutlinedIcon />
                                </IconButton>
                            </Grid>
                            <Grid item>
                                <a href="https://github.com/CoderAT13/FISCO-FINAL">
                                    <IconButton variant="contained" color="secondary" className={classes.button}>
                                        <GitHubIcon />
                                    </IconButton>
                                </a>
                            </Grid>
                            <Grid item>
                                <IconButton variant="contained" color="secondary" className={classes.button} onClick={() => this.setState({ loginS: false })}>
                                    <ExitToApp />
                                </IconButton>
                            </Grid>
                        </Grid>
                    </Toolbar>
                </AppBar>

                {/* 表格显示 */}
                <main className={classes.content}>
                    <div className={classes.toolbar} />
                    <Grid container spacing={24} direction="row" justify="center" alignItems="center">
                        <Grid item>
                            <VpnKeyOutlinedIcon color="primary" style={{width: 200, height: 200}}/>
                        </Grid>
                        <Grid item>
                            <Typography color="primary" variant="h3" gutterBottom>
                                {"供应链金融平台"}
                            </Typography>
                        </Grid>
                        
                    </Grid>
                    <Grid container spacing={24} direction="row" justify="center">
                        <Grid item>
                            <TransferCard classes={classes} isMaster={isMaster} onConfirm={this.handleTransfer} />
                        </Grid>
                        <Grid item>
                            { isMaster && <PayCard classes={classes} onConfirm={this.handlePay}/>}
                        </Grid>
                        <Grid item>
                            <SearchCard onConfirm={this.handleSearch} data={searchData} status={hasSearch} classes={classes}/>
                        </Grid>
                    </Grid>
                </main>
            </div>
        );
    }
}

Dashboard.propTypes = {
    classes: PropTypes.object.isRequired,
    container: PropTypes.object,
    theme: PropTypes.object.isRequired,
};


function mapStateToProps(state) {
    const { user, authentication } = state;
    return {
        user,
        authentication,
    };
}

const connectedDashboard = injectIntl(withStyles(styles)(connect(mapStateToProps)(Dashboard)));
export { connectedDashboard as Dashboard };