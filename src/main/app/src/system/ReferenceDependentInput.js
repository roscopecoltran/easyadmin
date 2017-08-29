import React, {Component} from 'react';
import {translate, SelectInput, ReferenceInput, required} from 'admin-on-rest';
import {url} from '../constants';

class ReferenceDependentInput extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        fields: null,
    }

    componentDidMount() {
        this.getFields(this.props.dependsOnValue);
    }

    getFields(entity){
        fetch(url + `/schemas/_fields?eid=`+entity)
            .then((response) => {
                return response.json()
            })
            .then((json) => {
                this.setState({
                    fields: json
                })
            });
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.dependsOnValue!=this.props.dependsOnValue)
            this.getFields(nextProps.dependsOnValue)
    }

    render() {
        let props = {...this.props};
        if (null === this.state.fields || this.state.fields === undefined) return null;
        return (
            <SelectInput {...props} choices={this.state.fields} optionText="label" optionValue="id"/>
        )
    }
}

ReferenceDependentInput.propTypes = ReferenceInput.propTypes;
ReferenceDependentInput.defaultProps = ReferenceInput.defaultProps;

export default ReferenceDependentInput;