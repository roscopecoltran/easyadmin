import React from "react";
import FlatButton from "material-ui/FlatButton";
import Popover from "material-ui/Popover";
import Menu from "material-ui/Menu";
import MenuItem from "material-ui/MenuItem";
import ContentAdd from "material-ui/svg-icons/content/add";
import ComponentType from "./ComponentType";
import {Link} from "react-router-dom";

const keys = Object.keys(ComponentType);
const arr = [];
keys.forEach(v => {
    arr.push({id: v, name: ComponentType[v]});
})
const styles = {
    floating: {
        margin: 0,
        top: 'auto',
        right: 20,
        bottom: 60,
        left: 'auto',
        position: 'fixed',
    },
    flat: {
        overflow: 'inherit',
    },
};
export default class AddFieldButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            open: false,
            props: props
        };
    }

    handleTouchTap = (event) => {
        // This prevents ghost click.
        event.preventDefault();

        this.setState({
            open: true,
            anchorEl: event.currentTarget,
        });
    };

    handleRequestClose = () => {
        this.setState({
            open: false,
        });
    };

    render() {
        return (
            <div>
                <FlatButton
                    onClick={this.handleTouchTap}
                    label="新增字段"
                    style={styles.flat}
                    icon={<ContentAdd/>}
                />
                <Popover
                    open={this.state.open}
                    anchorEl={this.state.anchorEl}
                    anchorOrigin={{horizontal: 'left', vertical: 'bottom'}}
                    targetOrigin={{horizontal: 'left', vertical: 'top'}}
                    onRequestClose={this.handleRequestClose}
                >
                    <Menu>
                        {arr.map((field) => (
                            <MenuItem key={field.id} primaryText={field.name} containerElement={<Link to={{
                                pathname: "/_fields/create",
                                data: {eid: this.state.props.record.id, component: field.id},
                            }}/>}/>
                        ))}
                    </Menu>
                </Popover>
            </div>
        );
    }
}