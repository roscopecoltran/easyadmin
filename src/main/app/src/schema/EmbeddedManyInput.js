import FlatButton from "material-ui/FlatButton";
import React, {Component} from "react";
import {Field, FieldArray} from "redux-form";
import PropTypes from "prop-types";

import ContentCreateIcon from "material-ui/svg-icons/content/create";
import ActionDeleteIcon from "material-ui/svg-icons/action/delete";

export class EmbeddedManyInput extends Component {
    renderList = ({fields}) => {
        const {children} = this.props;
        return (
            <div style={{margin: '1em'}}>
                {fields.map((member, index) =>
                    <div key={index} style={{display: 'inline-block', verticalAlign: 'bottom'}}>
                        <div style={{padding: '0 1em 1em 1em'}}>
                            {React.Children.map(children, input => input && (
                                <div key={input.props.source} className={`aor-input-${input.props.source}`}
                                     style={input.props.style}>
                                    <Field {...input.props} name={`${member}.${input.props.source}`}
                                           component={input.type}/>
                                </div>
                            ))}
                        </div>
                        <FlatButton
                            label="Remove"
                            icon={<ActionDeleteIcon/>}
                            onClick={() => fields.remove(index)}/>
                    </div>
                )}
                <FlatButton icon={<ContentCreateIcon/>} label="Add" onClick={() => fields.push({})}/>
            </div>
        )
    }


    render() {
        const {source} = this.props;

        return (
            <FieldArray name={source} component={this.renderList}/>
        )
    }
}

EmbeddedManyInput.propTypes = {
    addField: PropTypes.bool.isRequired,
    allowEmpty: PropTypes.bool.isRequired,
    basePath: PropTypes.string,
    children: PropTypes.node,
    label: PropTypes.string,
    meta: PropTypes.object,
    onChange: PropTypes.func,
    input: PropTypes.object,
    resource: PropTypes.string,
    source: PropTypes.string,
};

EmbeddedManyInput.defaultProps = {
    addField: false,
    allowEmpty: false,
};

export default EmbeddedManyInput;