
export function getItemTitleByLink(link: string, nbMenuItemArray: any[]): string {
    const item: any = getItemByLink(link, nbMenuItemArray);
    if (item) return item.title;
}

export function getItemByLink(link: string, nbMenuItemArray: any[]): any {
    return getItemBy((item) => item.link === link, nbMenuItemArray);
}

export function getItemBy(predicate, nbMenuItemArray: any[]): any {
    if (!nbMenuItemArray) {
        return null;
    }
    for (const nbMenuItem of nbMenuItemArray) {
        const found = nbMenuItemArray.find(predicate);
        if (found) {
            return found;
        } else {
            const foundInChildren = getItemBy(predicate, nbMenuItem.children);
            if (foundInChildren) return foundInChildren;
        }

    }
}
