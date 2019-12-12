import React from 'react';
import { Card, CardActions, CardContent } from '@material-ui/core';
import { AccountCircle, AttachMoney } from '@material-ui/icons';
import { Typography, TextField, Button, Grid } from '@material-ui/core';

class TransferCard extends React.Component {
    constructor(props) {
        super();
        this.state = {
            account: "",
            amount: ""
        };
    }

    handleText = (e) =>{
        this.setState({
            account: e.currentTarget.value
        })
    }

    handleAmount = (e) =>{
        this.setState({
            amount: e.currentTarget.value
        })
    }

    handleConfirm = () =>{
        const {account, amount} = this.state;
        if (amount == "" || account == "" || parseFloat(amount) <= 0){
            alert("参数错误，请检查");
            return;
        }
        this.props.onConfirm("account=" + account +"&amount=" + amount);

    }

    render() {
        const { isMaster, classes } = this.props;
        return (
            <Card className={classes.card}>
                <CardContent>
                    <Typography variant="h5" component="h2">
                        {isMaster ? "应收账款（债权）签发" : "应收账款（债权）转让"}
                    </Typography>
                    <Grid container spacing={8} alignItems="flex-end">
                        <Grid item>
                            <AccountCircle />
                        </Grid>
                        <Grid item>
                            <TextField label="账户名称" onChange={this.handleText}/>
                        </Grid>
                    </Grid>

                    <Grid container spacing={8} alignItems="flex-end">
                        <Grid item>
                            <AttachMoney />
                        </Grid>
                        <Grid item>
                            <TextField type="number" label="额度" onChange={this.handleAmount} />
                        </Grid>
                    </Grid>

                </CardContent>
                <CardActions>
                    <Button size="small" variant="contained" color="primary" onClick={this.handleConfirm}>Submit</Button>
                </CardActions>
            </Card>
        );
    }
}

const transferCard = TransferCard;
export { transferCard as TransferCard }