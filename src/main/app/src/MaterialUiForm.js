import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Field, reduxForm, formValueSelector} from 'redux-form';
import {RadioButton} from 'material-ui/RadioButton';
import MenuItem from 'material-ui/MenuItem';
import {AutoComplete as MUIAutoComplete} from 'material-ui';
import {
    AutoComplete,
    Checkbox,
    DatePicker,
    TimePicker,
    RadioButtonGroup,
    SelectField,
    Slider,
    TextField,
    Toggle,
} from 'redux-form-material-ui';
import injectTapEventPlugin from 'react-tap-event-plugin';

injectTapEventPlugin();

var url = "/schemas";
if (process.env.NODE_ENV === 'development') {
    url = "http://localhost:8080/schemas";
}
// validation functions
const required = value => (value == null ? 'Required' : undefined);
const email = value =>
    (value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value)
        ? 'Invalid email'
        : undefined);
// const fieldSchemas = [{
//     name: 'name',
//     component: 'TextField',
//     floatingLabelText: 'Name'
// }, {
//     name: 'driver',
//     component: 'SelectField',
//     floatingLabelText: 'driver',
//     items: [{value: 'v1', name: 'n1'}, {value: 'v2', name: 'n2'}]
// }, {
//     name: 'when',
//     component: 'DatePicker',
//     hintText: 'Day of delivery?',
// }, {
//     name: 'delivery',
//     component: 'RadioButtonGroup',
//     items: [{value: 'pickup', name: 'pickup'}, {value: 'delivery', name: 'Delivery'}]
// }, {
//     name: 'Thincrust',
//     component: 'Toggle',
//     label: 'Thin Crust',
//     labelPosition: 'right'
// }, {
//     name: 'at',
//     component: 'TimePicker',
//     hintText: 'At what time?',
// }, {
//     name: 'notes',
//     component: 'TextArea',
//     hintText: 'Notes',
//     floatingLabelText: 'Notes',
//     rows: 4
// }, {
//     name: 'pepperoni',
//     component: 'Checkbox',
//     label: 'pepperoni',
// }, {
//     name: 'referral',
//     floatingLabelText: 'aaa',
//     component: 'AutoComplete',
//     dataSource: [
//         {id: 0, name: 'Facebook'},
//         {id: 1, name: 'Yelp'},
//         {id: 2, name: 'TV Ad'},
//         {id: 3, name: 'Friend'},
//         {id: 4, name: 'Other'},
//     ]
// }, {
//     name: 'pizzas',
//     component: 'Slider',
//     defaultValue: 0,
//     min: 0,
//     max: 20,
//     step: 1
// }];
const renderMuiField = (member, index, fields) => (
    member.component === 'Slider' ? <div>
        <Field
            name={member.name}
            component={Slider}
            defaultValue={member.defaultValue}
            format={null}
            min={member.min}
            max={member.max}
            step={member.step}
        />
    </div> :
        member.component === 'TextField' ? <div>
            <Field
                name={member.name}
                component={ TextField }
                floatingLabelText={member.floatingLabelText}
            />
        </div> :
            member.component === 'TextArea' ? <div>
                <Field
                    name={member.name}
                    component={TextField}
                    hintText={member.hintText}
                    floatingLabelText={member.floatingLabelText}
                    multiLine
                    rows={member.rows}
                />
            </div> :
                member.component === 'SelectField' ? <div>
                    <Field
                        name={member.name}
                        component={ SelectField }
                        floatingLabelText={member.floatingLabelText}>
                        {member.items.map((item) => (
                            <MenuItem value={item.value} primaryText={item.name}/>
                        ))}
                    </Field>
                </div> :
                    member.component === 'DatePicker' ? <div>
                        <Field
                            name={member.name}
                            component={DatePicker}
                            format={null}
                            hintText={member.hintText}
                            validate={required}
                        />
                    </div> :
                        member.component === 'TimePicker' ? <div>
                            <Field
                                name={member.name}
                                component={TimePicker}
                                format={null}
                                defaultValue={null} // TimePicker requires an object,
                                // and redux-form defaults to ''
                                hintText={member.hintText}
                                validate={required}
                            />
                        </div> :
                            member.component === 'Checkbox' ? <div>
                                <Field name={member.name} component={Checkbox} label={member.label}/>
                            </div> :
                                member.component === 'RadioButtonGroup' ? <div>
                                    <Field name={member.name} component={RadioButtonGroup}>
                                        {member.items.map((item) => (
                                            <RadioButton value={item.value} label={item.name}/>
                                        ))}
                                    </Field>
                                </div> :
                                    member.component === 'AutoComplete' ? <div>
                                        <Field
                                            name={member.name}
                                            component={AutoComplete}
                                            floatingLabelText={member.floatingLabelText}
                                            openOnFocus
                                            filter={MUIAutoComplete.fuzzyFilter}
                                            dataSourceConfig={{text: 'name', value: 'id'}}
                                            dataSource={member.dataSource}
                                        />
                                    </div> :
                                        member.component === 'Toggle' ? <div>
                                            <Field
                                                name={member.name}
                                                component={Toggle}
                                                label={member.label}
                                                labelPosition={member.labelPosition}
                                            />
                                        </div> :
                                            <div>
                                                <Field
                                                    name={member.name}
                                                    component={ TextField }
                                                    floatingLabelText={member.floatingLabelText}
                                                />
                                            </div>
);


class Form extends Component {
    state = {fieldSchemas:[]};

    componentWillMount() {
        this.getFieldSchemas();
    }

    getFieldSchemas() {
        fetch(url + `/`)
            .then((response) => {
                return response.json()
            })
            .then((json) => {
                this.setState({
                    fieldSchemas: json
                })
            });
    }

    render() {
        const {
            fieldSchemas,
        } = this.state;
        const {handleSubmit, pristine, reset, submitting} = this.props;
        return (
            <form onSubmit={handleSubmit}>
                {fieldSchemas.map(renderMuiField) }
                <div>
                    <button type="submit" disabled={submitting}>Submit</button>
                    <button
                        type="button"
                        disabled={pristine || submitting}
                        onClick={reset}
                    >
                        Clear
                    </button>
                </div>
            </form>
        );
    }
}

const selector = formValueSelector('example');

Form = connect(state => ({
    numPizzas: selector(state, 'pizzas'),
}))(Form);

Form = reduxForm({
    form: 'example',
    initialValues: {
        delivery: 'delivery',
        name: 'Jane Doe',
        cheese: 'Cheddar',
        pizzas: 1,
    },
})(Form);

export default Form;
