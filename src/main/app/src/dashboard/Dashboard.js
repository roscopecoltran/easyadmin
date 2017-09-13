import React, {Component} from "react";
import {AppBarMobile} from 'admin-on-rest';
import withWidth from "material-ui/utils/withWidth";
import Line from './Line';
import Bar from './Bar';
import RadarChart from './RadarChart';
const styles = {
    welcome: {marginBottom: '2em'},
    flex: {display: 'flex'},
    leftCol: {flex: 1, marginRight: '1em'},
    rightCol: {flex: 1, marginLeft: '1em'},
    singleCol: {marginTop: '2em'},
};
class Dashboard extends Component {
    state = {};

    componentDidMount() {
        this.getRateOfCreditSuccess();
        this.getCreditAvgTimes();
    }

    getRateOfCreditSuccess() {

    }

    getCreditAvgTimes() {

    }

    render() {
        const { width } = this.props;
        return (
            <div>
                {width === 1 && <AppBarMobile title="DataHive Dashboard" />}
                <div style={styles.flex}>
                    <div style={styles.leftCol}>
                        <div style={styles.flex} >
                            <Line/>
                        </div>
                        <div style={styles.singleCol}>
                            <Bar />
                        </div>
                        <div style={styles.singleCol}>
                            <RadarChart />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withWidth()(Dashboard);