import React, {Component} from "react";
import {ReferenceInput, SelectInput} from "admin-on-rest";
import {url} from "../constants";
import {httpClient} from "./../authClient";
class ReferenceDependentInput extends Component {
    state = {
        fields: null,
    }

    componentDidMount() {
        this.getFields(this.props.dependsOnValue);
    }

    getFields(entity) {
        httpClient(url + `/schemas/fields?entityName=` + entity)
            .then((response) => {
                return response.json
            })
            .then((json) => {
                this.setState({
                    fields: json
                })
            });
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.dependsOnValue !== this.props.dependsOnValue)
            this.getFields(nextProps.dependsOnValue)
    }

    render() {
        let props = {...this.props};
        if (null === this.state.fields || this.state.fields === undefined) return null;
        return (
            <SelectInput {...props} choices={this.state.fields} optionText="label" optionValue="name"/>
        )
    }
}

ReferenceDependentInput.propTypes = ReferenceInput.propTypes;
ReferenceDependentInput.defaultProps = ReferenceInput.defaultProps;

export default ReferenceDependentInput;