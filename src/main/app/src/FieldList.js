import React, {Component} from 'react';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn,
} from 'material-ui/Table';
var url = "/schemas";
if (process.env.NODE_ENV === 'development') {
    url = "http://localhost:8080/" + url;
}

const renderTable = (item, index, fieldSchemas) => (
    <TableRow>
        <TableRowColumn>{item.name}</TableRowColumn>
        <TableRowColumn>{item.component}</TableRowColumn>
        <TableRowColumn>{item.floatingLabelText}</TableRowColumn>
    </TableRow>
)
export default class FieldList extends Component {
    state = {
        selected: [1],
        fieldSchemas:[]
    };
    isSelected = (index) => {
        return this.state.selected.indexOf(index) !== -1;
    };
    handleRowSelection = (selectedRows) => {
        this.setState({
            selected: selectedRows,
        });
    };
    renderTable = (item, index, fieldSchemas) => (
        <TableRow selected = {this.isSelected(index-1)}>
            <TableRowColumn>{item.name}</TableRowColumn>
            <TableRowColumn>{item.component}</TableRowColumn>
            <TableRowColumn>{item.floatingLabelText}</TableRowColumn>
        </TableRow>
    )
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
    isSelected = (index) => {
        return this.state.selected.indexOf(index) !== -1;
    };

    handleRowSelection = (selectedRows) => {
        this.setState({
            selected: selectedRows,
        });
    };

    render() {
        return (
            <Table onRowSelection={this.handleRowSelection}>
                <TableHeader>
                    <TableRow>
                        <TableHeaderColumn>字段Id</TableHeaderColumn>
                        <TableHeaderColumn>字段类型</TableHeaderColumn>
                        <TableHeaderColumn>标签</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {this.state.fieldSchemas.map(renderTable)}
                </TableBody>
            </Table>
        );
    }
}