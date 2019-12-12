import React from 'react';
import { Card, CardActions, CardContent } from '@material-ui/core';
import { Search } from '@material-ui/icons';
import { Typography, TextField, Button } from '@material-ui/core';

class PayCard extends React.Component {
    constructor(props) {
        super();
        this.state = {
        };
    }

    render() {
        const { classes, onConfirm } = this.props;
        return (

            <Card className={classes.card}>
                <CardContent>
                    <Typography variant="h5" component="h2">
                        支付
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small" variant="contained" color="primary" onClick={onConfirm}>Submit</Button>
                </CardActions>
            </Card>

        );
    }
}

const payCard = PayCard;
export { payCard as PayCard }