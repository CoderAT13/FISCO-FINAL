import React from 'react';
import { Card, CardActions, CardContent } from '@material-ui/core';
import { Search } from '@material-ui/icons';
import { Typography, TextField, Button, Grid } from '@material-ui/core';

class SearchCard extends React.Component {
    constructor(props) {
        super();
        this.state = {
            account: ""
        };
    }

    handleSearch = () =>{
        const { onConfirm } = this.props;
        onConfirm("account=" + this.state.account);
    }

    handleText = (e) =>{
        this.setState({
            account: e.currentTarget.value
        })
    }

    render() {
        const { classes, data, status } = this.props;
        const { } = this.state;
        return (
            <Card className={classes.card}>
                <CardContent>
                    <Typography variant="h5" component="h2">
                        应收账款（债权）查询
                    </Typography>
                    <Grid container spacing={8} alignItems="flex-end">
                        <Grid item>
                            <Search />
                        </Grid>
                        <Grid item>
                            <TextField label="名称搜索" onChange={this.handleText}/>
                        </Grid>
                    </Grid>
                    {
                        status &&
                        <div>
                            <Grid container spacing={8} alignItems="flex-end">
                                <Grid item>
                                    <Typography >
                                        {"查询状态："}
                                    </Typography>
                                </Grid>
                                <Grid item>
                                    <Typography >
                                        {data.status == -1 ? "用户不存在" : "成功"}
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Grid container spacing={8} alignItems="flex-end">
                                <Grid item>
                                    <Typography >
                                        {"额度："}
                                    </Typography>
                                </Grid>
                                <Grid item>
                                    <Typography >
                                        {data.amount}
                                    </Typography>
                                </Grid>
                            </Grid>
                        </div>
                    }

                </CardContent>
                <CardActions>
                    <Button size="small" variant="contained" color="primary" onClick={this.handleSearch}>Submit</Button>
                </CardActions>
            </Card>
        );
    }
}

const searchCard = SearchCard;
export { searchCard as SearchCard }