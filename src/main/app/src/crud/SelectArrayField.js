import React from "react";
import PropTypes from "prop-types";
import Chip from "material-ui/Chip";

const styles = {
    chip: {
        margin: 4,
    },
    wrapper: {
        display: 'flex',
        flexWrap: 'wrap',
    },
};

const SelectArrayField = ({source, record, choices, elStyle, optionValue, optionText, translate, translateChoice = {}}) => {
    const value = record[source];
    if (!value) return null;
    return <div style={styles.wrapper}>{value.map((v, index) => {
        const choice = choices.find(c => c[optionValue] === v);
        return <Chip key={index} style={styles.chip}>{choice[optionText]}</Chip>
    })}</div>;
};

SelectArrayField.propTypes = {
    label: PropTypes.string,
    record: PropTypes.object,
    source: PropTypes.string.isRequired,
};

export default SelectArrayField;