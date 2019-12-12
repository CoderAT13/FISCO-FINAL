import React from 'react';
import PropTypes from 'prop-types';
import { withStyles, Grid, Paper, Typography, LinearProgress } from '@material-ui/core';
import { blue, pink, teal, indigo } from '@material-ui/core/colors';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { userActions } from '../_actions';
import { Dashboard } from './Dashboard';
import { Redirect } from 'react-router-dom';

const themes = createMuiTheme({
    typography: {
        useNextVariants: true,
    },
    palette: {
        primary: indigo,
        secondary: {
            main: "#ffffff"
        }
    },
});
const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    container: {
        //backgroundColor: '#2196f3'
    },
    content: {
        padding: theme.spacing.unit,
    },
    card: {
        maxWidth: 360,
        margin: theme.spacing.unit * 1.75,
        padding: theme.spacing.unit * 1.5,
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
    progress: {
        marginTop: -theme.spacing.unit,
        marginLeft: -theme.spacing.unit,
        marginRight: -theme.spacing.unit,
    },
});
class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount() {
        // const { dispatch } = this.props;
        // dispatch();
    }

    componentWillUnmount() {
    }


    render() {
        const { user, classes } = this.props;

        return (
            <MuiThemeProvider theme={themes}>
                <Dashboard theme={themes}/>
            </MuiThemeProvider>
        )

    }
}

function mapStateToProps(state) {
    const { authentication } = state;
    return {
        authentication,
    };
}

HomePage.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};
const connectedHomePage = withStyles(styles, { withTheme: true })(connect(mapStateToProps)(HomePage));
export { connectedHomePage as HomePage }; 