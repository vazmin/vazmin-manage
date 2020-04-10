import {Params} from '@angular/router';

export class MenuItem {

  // @ts-ignore
  id: string;
  /**
   * Item Title
   * @type {string}
   */
  // @ts-ignore
  title: string;
  /**
   * Item relative link (for routerLink)
   * @type {string}
   */
  link?: string;
  /**
   * Item URL (absolute)
   * @type {string}
   */
  url?: string;
  /**
   * Icon class name or icon config object
   * @type {string }
   */
  icon?: string ;
  /**
   * Expanded by default
   * @type {boolean}
   */
  expanded?: boolean;
  /**
   * Children items
   * @type {List<NbMenuItem>}
   */
  children?: MenuItem[];
  /**
   * HTML Link target
   * @type {string}
   */
  target?: string;
  /**
   * Hidden Item
   * @type {boolean}
   */
  hidden?: boolean;
  /**
   * Item is selected when partly or fully equal to the current url
   * @type {string}
   */
  pathMatch?: 'full' | 'prefix' = 'full';
  /**
   * Where this is a home item
   * @type {boolean}
   */
  home?: boolean;
  /**
   * Whether the item is just a group (non-clickable)
   * @type {boolean}
   */
  group?: boolean;
  /** Whether the item skipLocationChange is true or false
   *@type {boolean}
   */
  skipLocationChange?: boolean;
  /** Map of query parameters
   *@type {Params}
   */
  queryParams?: Params;
  parent?: MenuItem;
  selected?: boolean;
  data?: any;
  fragment?: string;

  /**
   * @returns item parents in top-down order
   */
  static getParents(item: MenuItem): MenuItem[] {
    const parents = [];

    let parent = item.parent;
    while (parent) {
      parents.unshift(parent);
      parent = parent.parent;
    }

    return parents;
  }

  static isParent(item: MenuItem, possibleChild: MenuItem): boolean {
    return possibleChild.parent
      ? possibleChild.parent === item || this.isParent(item, possibleChild.parent)
      : false;
  }
}
