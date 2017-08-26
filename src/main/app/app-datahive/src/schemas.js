export default [
    {
        "id": "10",
        "label": "客户",
        "name": "customer",
        "fields": [
            {
                "entity": "10",
                "id": "15",
                "name": "name",
                "component": "Text",
                "label": "姓名",
                "inputType": "text",
                "showInList": true
            },
            {
                "entity": "10",
                "id": "16",
                "name": "email",
                "component": "Text",
                "label": "邮箱",
                "inputType": "email",
                "showInList": true
            }
        ],
        "crud": [
            "c",
            "r",
            "u",
            "d"
        ],
        "redirect": "list"
    },
    {
        "id": "11",
        "label": "产品",
        "name": "product",
        "fields": [
            {
                "entity": "11",
                "id": "17",
                "name": "productName",
                "component": "Text",
                "label": "产品名称",
                "inputType": "text",
                "showInList": true
            }
        ],
        "crud": [
            "c",
            "r",
            "u",
            "d"
        ],
        "redirect": "list"
    }
];