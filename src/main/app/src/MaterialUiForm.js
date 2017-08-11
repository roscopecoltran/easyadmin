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
import FlatButton from 'material-ui/FlatButton';
injectTapEventPlugin();

var url = "/schemas";
if (process.env.NODE_ENV === 'development') {
    url = "http://localhost:8080/" + url;
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
const renderSliderField = (field) => {
    return <Field
        name={field.name}
        component={Slider}
        defaultValue={field.defaultValue}
        format={null}
        min={field.min}
        max={field.max}
        step={field.step}
    />
};
const renderTextField = (field) => {
    return <Field
        name={field.name}
        component={ TextField }
        floatingLabelText={field.floatingLabelText}
        validate={[required, email]}
        defaultValue={field.defaultValue}
    />
};

const renderTextAreaField = (field) => {
    return <Field
        name={field.name}
        component={TextField}
        hintText={field.hintText}
        floatingLabelText={field.floatingLabelText}
        defaultValue={field.defaultValue}
        multiLine
        rows={field.rows}
    />
};
const renderSelectField = (field) => {
    return <Field
        name={field.name}
        component={ SelectField }
        floatingLabelText={field.floatingLabelText}>
        {field.items.map((item, index) => (
            <MenuItem key={index} value={item.value} primaryText={item.name}/>
        ))}
    </Field>
}

const renderDatePickerField = (field) => {
    return <Field
        name={field.name}
        component={DatePicker}
        format={null}
        hintText={field.hintText}
        validate={required}
    />
}
const renderTimePickerField = (field) => {
    return <Field
        name={field.name}
        component={TimePicker}
        format={null}
        // and redux-form defaults to ''
        hintText={field.hintText}
        validate={required}
    />
}

const renderCheckboxField = (field) => {
    return <Field name={field.name} component={Checkbox} label={field.label}/>
}

const renderRadioButtonGroupField = (field) => {
    return <Field name={field.name} component={RadioButtonGroup}>
        {field.items.map((item, index) => (
            <RadioButton key={index} value={item.value} label={item.name}/>
        ))}
    </Field>
}

const renderAutoCompleteField = (field) => {
    return <Field
        name={field.name}
        component={AutoComplete}
        hintText={field.hintText}
        floatingLabelText={field.floatingLabelText}
        openOnFocus
        filter={MUIAutoComplete.fuzzyFilter}
        dataSourceConfig={{text: 'name', value: 'id'}}
        dataSource={field.dataSource}
    />
}
const renderToggleField = (field) => {
    return <Field
        name={field.name}
        component={Toggle}
        label={field.label}
        labelPosition={field.labelPosition}
    />
}
const renderMuiField = (member, index, fields) => (
    <div key={index}>
        {member.component === 'Slider' ? renderSliderField(member) :
            member.component === 'TextField' ? renderTextField(member) :
                member.component === 'TextArea' ? renderTextAreaField(member) :
                    member.component === 'SelectField' ? renderSelectField(member) :
                        member.component === 'DatePicker' ? renderDatePickerField(member) :
                            member.component === 'TimePicker' ? renderTimePickerField(member) :
                                member.component === 'Checkbox' ? renderCheckboxField(member) :
                                    member.component === 'RadioButtonGroup' ? renderRadioButtonGroupField(member) :
                                        member.component === 'AutoComplete' ? renderAutoCompleteField(member) :
                                            member.component === 'Toggle' ? renderToggleField(member) :
                                                renderTextField(member)}
    </div>
);


class Form extends Component {
    state = {fieldSchemas: []};

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
                    <FlatButton label="Submit" type="submit" primary={true} disabled={submitting}/>
                    <FlatButton label="Clear" secondary={true} disabled={pristine || submitting} onClick={reset}/>
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
