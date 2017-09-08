import {englishMessages} from "admin-on-rest";
import zhcnMsg from "aor-language-chinese";
import customEnglishMessages from "./en";
import customChineseMessages from "./zh-hans";

export default {
    en: {...englishMessages, ...customEnglishMessages},
    zh: {...zhcnMsg, ...customChineseMessages}
};