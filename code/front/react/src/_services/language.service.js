import zh_CN from '../_locale/zh_CN';
import en_US from '../_locale/en_US';
export const languageService = {
    setLanguage,
    getLanguage
};

function setLanguage(language) {
    switch (language) {
        case "en_US":
            localStorage.setItem('language', "en_US");
            break;
        case "zh_CN":
            localStorage.setItem('language', "zh_CN");
            break;
        default:
            localStorage.setItem('language', "en_US");
    };
}

function getLanguage() {
    const language = localStorage.getItem('language') || navigator.language;
    if (language) {
        switch (language) {
            case "en_US":
                return en_US;
            case "en":
                return en_US;
            case "zh_CN":
                return zh_CN;
            case "zh":
                return zh_CN;
            default:
                return en_US;
        }
    }
    return en_US;
}
