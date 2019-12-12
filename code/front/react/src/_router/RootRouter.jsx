import { Switch, Route, Redirect } from 'react-router-dom';
import React from 'react';
import { injectIntl } from "react-intl";
import { HomePage } from '../LicencePage';
// 按照 Layout 归类分组可以按照如下方式组织路由
class RootRouter extends React.Component {
    constructor(props) {
        super(props);
        document.title = "供应链金融平台";
    }

    render() {
        //console.log(user);
        return (
            <Switch>
                <Route exact path="/home"  component={HomePage} />
                <Redirect to={{ pathname: '/home' }} />
            </Switch>
        );
        
    };
};
const injectRootRouter = injectIntl(RootRouter);
export { injectRootRouter as RootRouter };
